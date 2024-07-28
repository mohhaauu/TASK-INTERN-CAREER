import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class LoginGUI extends Application {
    private User user; // Instance variable to hold the user data

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 0);
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        loginButton.setOnAction(e -> {
            String email = emailInput.getText();
            String password = passwordInput.getText();

            if (email.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields");
                return;
            }

            try {
                User user = Database.validateLogin(email, password);
                if (user != null) {
                    setUser(user); // Set the user
                    if (user.getRole().equals("student")) {
                        selectExamForStudent(user.getId());
                    } else if (user.getRole().equals("teacher")) {
                        ExamCreation createExamGUI = new ExamCreation(user.getId());
                        createExamGUI.start(new Stage());
                    }
                    primaryStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid email or password");
                }
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Login Error", "An error occurred: " + ex.getMessage());
            }
        });

        grid.getChildren().addAll(emailLabel, emailInput, passwordLabel, passwordInput, loginButton);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void selectExamForStudent(int studentId) {
        Stage examSelectionStage = new Stage();
        examSelectionStage.setTitle("Select Exam");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label selectExamLabel = new Label("Select Exam:");
        GridPane.setConstraints(selectExamLabel, 0, 0);

        ComboBox<Integer> examComboBox = new ComboBox<>();
        GridPane.setConstraints(examComboBox, 1, 0);

        try {
            List<Integer> examIds = Database.getAllExamIds();
            examComboBox.getItems().addAll(examIds);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load exams: " + e.getMessage());
        }

        Button startExamButton = new Button("Start Exam");
        GridPane.setConstraints(startExamButton, 1, 1);

        startExamButton.setOnAction(e -> {
            Integer selectedExamId = examComboBox.getValue();
            if (selectedExamId != null) {
                ExamTaking examTaking = new ExamTaking(studentId, selectedExamId);
                try {
                    examTaking.start(new Stage());
                } catch (Exception ee) {
                    showAlert(Alert.AlertType.ERROR, "Exam Error", "Failed to start the exam: " + ee.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select an exam.");
            }
        });

        grid.getChildren().addAll(selectExamLabel, examComboBox, startExamButton);

        Scene scene = new Scene(grid, 300, 150);
        examSelectionStage.setScene(scene);
        examSelectionStage.show();
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
