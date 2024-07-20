import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;

public class UserRegistrationGUI extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
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

        TextField yearOfStudyField = new TextField();
        yearOfStudyField.setPromptText("Year of Study");
        yearOfStudyField.setVisible(false); // initially hidden

        TextField majorField = new TextField();
        majorField.setPromptText("Major");
        majorField.setVisible(false); // initially hidden

        TextField departmentField = new TextField();
        departmentField.setPromptText("Department");
        departmentField.setVisible(false); // initially hidden

        TextField yearsOfExperienceField = new TextField();
        yearsOfExperienceField.setPromptText("Years of Experience");
        yearsOfExperienceField.setVisible(false); // initially hidden

        roleComboBox.setOnAction(e -> {
            String role = roleComboBox.getValue();
            if ("Student".equals(role)) {
                yearOfStudyField.setVisible(true);
                majorField.setVisible(true);
                departmentField.setVisible(false);
                yearsOfExperienceField.setVisible(false);
            } else if ("Teacher".equals(role)) {
                yearOfStudyField.setVisible(false);
                majorField.setVisible(false);
                departmentField.setVisible(true);
                yearsOfExperienceField.setVisible(true);
            }
        });

        Button registerButton = new Button("Register");
        Button loginButton = new Button("Login");

        vbox.getChildren().addAll(firstNameField, lastNameField, emailField, passwordField, confirmPasswordField,
                roleComboBox, yearOfStudyField, majorField, departmentField, yearsOfExperienceField, registerButton,
                loginButton);

        loginButton.setOnAction(e -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.start(primaryStage);
        });
        registerButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String role = roleComboBox.getValue();
            String username = lastName + role + "@school.ac.za";

            if (password.equals(confirmPassword)) {
                if ("Student".equals(role)) {
                    int yearOfStudy = Integer.parseInt(yearOfStudyField.getText());
                    String major = majorField.getText();
                    Student student = new Student(firstName, lastName, email, password, username, yearOfStudy, major);
                    try {
                        Database.addStudent(student);
                        showAlert("Registration successful! Your username is: " + username);
                        primaryStage.close();
                        LoginGUI loginGUI = new LoginGUI();
                        loginGUI.start(new Stage());
                    } catch (SQLException ex)
                    {
                        showAlert("Error during registration: " + ex.getMessage());
                    }
                } else if ("Teacher".equals(role)) {
                    String department = departmentField.getText();
                    int yearsOfExperience = Integer.parseInt(yearsOfExperienceField.getText());
                    Teacher teacher = new Teacher(firstName, lastName, email, password, username, department, yearsOfExperience);
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

        Scene scene = new Scene(vbox, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String message) {
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
