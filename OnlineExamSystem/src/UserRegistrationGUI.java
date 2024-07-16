import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;

public class UserRegistrationGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Registration");

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // UI components for registration
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Student", "Teacher");
        roleComboBox.setPromptText("Role");

        Button registerButton = new Button("Register");

        vbox.getChildren().addAll(firstNameField, lastNameField, emailField, passwordField, confirmPasswordField,
                                    roleComboBox, registerButton);

        registerButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String role = roleComboBox.getValue();

            if (password.equals(confirmPassword)) {
                String username = lastName + role + "@school.ac.za";

                if (role.equals("Student")) {
                    User student = new Student(1, firstName, lastName, email, password, username, 1, "Undeclared");
                    try {
                        Database.addStudent(student);
                        showAlert("Registration successful! Your username is: " + username);
                        primaryStage.close();
                        LoginGUI loginGUI = new LoginGUI();
                        loginGUI.start(new Stage());
                    } catch (SQLException ex) {
                        showAlert("Error during registration: " + ex.getMessage());
                    }
                } else if (role.equals("Teacher")) {
                    User teacher = new Teacher(1, firstName, lastName, email, password, username,
                                         0);
                    try {
                        Database.addTeacher(teacher);
                        showAlert("Registration successful! Your username is: " + username);
                        primaryStage.close();
                        LoginGUI loginGUI = new LoginGUI();
                        loginGUI.start(new Stage());
                    } catch (SQLException ex) {
                        showAlert("Error during registration: " + ex.getMessage());
                    }
                }
            } else {
                showAlert("Passwords do not match.");
            }
        });

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
