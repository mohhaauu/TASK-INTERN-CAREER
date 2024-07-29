import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A JavaFX application that allows a student to take an exam.
 * This application displays questions and manages the exam timing.
 */
public class ExamTaking extends Application {
    private final int studentId;
    private final int examId;
    private List<Questions> questions;
    private Timer timer;
    private int remainingTime; // in seconds

    /**
     * Constructs an ExamTaking instance with the specified student ID and exam ID.
     *
     * @param studentId the ID of the student taking the exam
     * @param examId the ID of the exam
     */
    public ExamTaking(int studentId, int examId) {
        this.studentId = studentId;
        this.examId = examId;
    }

    /**
     * Initializes and shows the exam-taking UI.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Take Exam");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Exam exam = null;
        try {
            exam = Database.getExamById(examId);
            questions = Database.getQuestionsByExamId(examId);

            if (questions.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "No Questions", "No questions found for this exam.");
                return;
            }

            remainingTime = exam.getDuration() * 60; // Convert minutes to seconds

        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load exam: " + ex.getMessage());
            return;
        }

        Label examTitleLabel = new Label("Exam: " + exam.getTitle());
        GridPane.setConstraints(examTitleLabel, 0, 0);
        grid.getChildren().add(examTitleLabel);

        int row = 1;
        for (Questions question : questions) {
            Label questionLabel = new Label(question.getQuestionText());
            GridPane.setConstraints(questionLabel, 0, row);
            grid.getChildren().add(questionLabel);

            TextField answerInput = new TextField();
            answerInput.setId("question-" + question.getId());
            GridPane.setConstraints(answerInput, 1, row);
            grid.getChildren().add(answerInput);

            row++;
        }

        Label timerLabel = new Label();
        GridPane.setConstraints(timerLabel, 1, row);
        grid.getChildren().add(timerLabel);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, row + 1);
        submitButton.setOnAction(e -> submitExam(primaryStage));

        grid.getChildren().add(submitButton);
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        startTimer(timerLabel, primaryStage);
    }

    /**
     * Starts the countdown timer for the exam and updates the timer label every second.
     * Submits the exam when the time is up.
     *
     * @param timerLabel the label displaying the remaining time
     * @param primaryStage the primary stage for this application
     */
    private void startTimer(Label timerLabel, Stage primaryStage) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                remainingTime--;
                int minutes = remainingTime / 60;
                int seconds = remainingTime % 60;

                Platform.runLater(() -> timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds)));

                if (remainingTime <= 0) {
                    timer.cancel();
                    Platform.runLater(() -> submitExam(primaryStage));
                }
            }
        }, 0, 1000);
    }

    /**
     * Submits the exam, calculates the score, and saves the results to the database.
     * Displays the exam results and feedback to the user.
     *
     * @param primaryStage the primary stage for this application
     */
    private void submitExam(Stage primaryStage) {
        if (timer != null) {
            timer.cancel();
        }

        // Collect answers and calculate score
        int score = 0;
        int totalMarks = 0;
        StringBuilder feedback = new StringBuilder();
        for (Questions question : questions) {
            TextField answerInput = (TextField) primaryStage.getScene().lookup("#question-" + question.getId());
            if (answerInput != null) {
                String answer = answerInput.getText();
                totalMarks += question.getMarks(); // Accumulate total marks
                if (question.getCorrectAnswer().equals(answer)) {
                    score += question.getMarks(); // Add question marks to score if correct
                    feedback.append("Question ").append(question.getId()).append(": Correct (Marks: ")
                            .append(question.getMarks()).append(")\n");
                } else {
                    feedback.append("Question ").append(question.getId()).append(": Incorrect (Correct Answer: ")
                            .append(question.getCorrectAnswer()).append(")\n");
                }

                // Instantiate and save StudentAnswer for each question
                StudentAnswer studentAns = new StudentAnswer(studentId, question.getId(), answer, question.getMarks());

                // Debugging statement to log the questionId and other info
                System.out.println("Saving answer for studentId: " + studentId + ", questionId: " + question.getId() + ", answer: " + answer);

                if (question.getId() <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Submission Error", "Invalid question_id: " + question.getId());
                    return;
                }

                try {
                    Database.saveStudentAnswer(studentAns);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Submission Error", "An error occurred while saving answers: " + ex.getMessage());
                }
            }
        }

        // Create ExamResult object with the required parameters
        ExamResult examResults = new ExamResult(studentId, examId, totalMarks, feedback.toString());

        try {
            Database.saveExamResults(examResults); // Save exam results to the database
            showAlert(Alert.AlertType.INFORMATION, "Exam Submitted", "Your score: " + score + " out of " + totalMarks + "\nFeedback:\n" + feedback);
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the stack trace for debugging
            showAlert(Alert.AlertType.ERROR, "Submission Error", "An error occurred: " + ex.getMessage());
        }

        primaryStage.close();
    }

    /**
     * Shows an alert dialog with the specified type, title, and message.
     *
     * @param alertType the type of alert to show
     * @param title the title of the alert
     * @param message the content of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Launches the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
