import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExamTaking extends Application {
    private final int studentId;
    private final int examId;
    private List<Questions> questions;
    private Timer timer;
    private int remainingTime; // in seconds

    public ExamTaking(int studentId, int examId) {
        this.studentId = studentId;
        this.examId = examId;
    }

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
            remainingTime = exam.getDuration() * 60; // Convert minutes to seconds
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load exam: " + ex.getMessage());
            return;
        }

        Label examTitleLabel = new Label("Exam: " + examId);
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
        GridPane.setConstraints(timerLabel, 1, questions.size() * 2);
        grid.getChildren().add(timerLabel);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, questions.size() * 2 + 1);
        submitButton.setOnAction(e -> submitExam(primaryStage));

        grid.getChildren().add(submitButton);
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        startTimer(timerLabel, primaryStage);
    }

    private void startTimer(Label timerLabel, Stage primaryStage) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                remainingTime--;
                int minutes = remainingTime / 60;
                int seconds = remainingTime % 60;
                timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));

                if (remainingTime <= 0) {
                    timer.cancel();
                    submitExam(primaryStage);
                }
            }
        }, 0, 1000);
    }

    private void submitExam(Stage primaryStage) {
        timer.cancel();

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
                    feedback.append("Question ").append(question.getId()).append(": Correct (Marks: ").append(question.getMarks()).append(")\n");
                } else {
                    feedback.append("Question ").append(question.getId()).append(": Incorrect (Correct Answer: ").append(question.getCorrectAnswer()).append(")\n");
                }

                // Instantiate and save StudentAnswer for each question
                StudentAnswer studentAns = new StudentAnswer(studentId, question.getId(), answer, question.getMarks());
                try {
                    Database.saveStudentAnswer(studentAns);
                } catch (SQLException ex) {
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
            showAlert(Alert.AlertType.ERROR, "Submission Error", "An error occurred: " + ex.getMessage());
        }

        primaryStage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
