import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * The ExamCreation class provides a JavaFX application for creating exams and adding questions to them.
 * This class allows teachers to create a new exam and add questions to it via a graphical user interface.
 */
public class ExamCreation extends Application
{
    private int currentExamId = -1;
    private int teacherId;

    /**
     * Constructor for ExamCreation.
     *
     * @param teacherId The ID of the teacher creating the exam.
     */
    public ExamCreation(int teacherId)
    {
        this.teacherId = teacherId;
    }

    /**
     * Starts the JavaFX application, sets up the user interface, and handles user interactions.
     *
     * @param primaryStage The primary stage for this application.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public void start(Stage primaryStage) throws SQLException
    {
        primaryStage.setTitle("Create Exam");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label titleLabel = new Label("Title:");
        GridPane.setConstraints(titleLabel, 0, 0);
        TextField titleInput = new TextField();
        GridPane.setConstraints(titleInput, 1, 0);

        Label durationLabel = new Label("Duration (minutes):");
        GridPane.setConstraints(durationLabel, 0, 1);
        TextField durationInput = new TextField();
        GridPane.setConstraints(durationInput, 1, 1);

        Button createExamButton = new Button("Create Exam");
        GridPane.setConstraints(createExamButton, 1, 2);

        Label questionLabel = new Label("Question:");
        GridPane.setConstraints(questionLabel, 0, 3);
        TextField questionInput = new TextField();
        GridPane.setConstraints(questionInput, 1, 3);

        Label answerLabel = new Label("Correct Answer:");
        GridPane.setConstraints(answerLabel, 0, 4);
        TextField answerInput = new TextField();
        GridPane.setConstraints(answerInput, 1, 4);

        Label marksLabel = new Label("Marks:");
        GridPane.setConstraints(marksLabel, 0, 5);
        TextField marksInput = new TextField();
        GridPane.setConstraints(marksInput, 1, 5);

        Button addQuestionButton = new Button("Add Question");
        GridPane.setConstraints(addQuestionButton, 1, 6);

        createExamButton.setOnAction(e ->
        {
            try
            {
                String title = titleInput.getText();
                int duration = Integer.parseInt(durationInput.getText());
                Database.addExam(teacherId, title, duration);
                currentExamId = Database.getLastInsertedExamId();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Exam created successfully with ID: " + currentExamId);
                alert.show();
            } catch (SQLException ex)
            {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed to create exam.");
                alert.show();
            }
        });

        addQuestionButton.setOnAction(e ->
        {
            try
            {
                if (currentExamId == -1)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please create an exam first.");
                    alert.show();
                    return;
                }
                String questionText = questionInput.getText();
                String correctAnswer = answerInput.getText();
                int marks = Integer.parseInt(marksInput.getText());
                Questions question = new Questions(0, currentExamId, questionText, marks, correctAnswer);
                Database.addQuestionToExam(currentExamId, question);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Question added successfully.");
                alert.show();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed to add question.");
                alert.show();
            }
        });

        grid.getChildren().addAll(titleLabel, titleInput, durationLabel, durationInput, createExamButton, questionLabel, questionInput, answerLabel, answerInput, marksLabel, marksInput, addQuestionButton);

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
