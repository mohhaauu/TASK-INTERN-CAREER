import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.SQLException;

public class UserRegistrationGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Registration");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label firstNameLabel = new Label("First Name:");
        GridPane.setConstraints(firstNameLabel, 0, 0);
        TextField firstNameInput = new TextField();
        GridPane.setConstraints(firstNameInput, 1, 0);

        Label lastNameLabel = new Label("Last Name:");
        GridPane.setConstraints(lastNameLabel, 0, 1);
        TextField lastNameInput = new TextField();
        GridPane.setConstraints(lastNameInput, 1, 1);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 2);
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 2);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 3);

        Label roleLabel = new Label("Role:");
        GridPane.setConstraints(roleLabel, 0, 4);
        ChoiceBox<String> roleChoiceBox = new ChoiceBox<>();
        roleChoiceBox.getItems().addAll("student", "teacher");
        GridPane.setConstraints(roleChoiceBox, 1, 4);

        // Additional fields for students
        Label yearOfStudyLabel = new Label("Year of Study:");
        GridPane.setConstraints(yearOfStudyLabel, 0, 5);
        TextField yearOfStudyInput = new TextField();
        GridPane.setConstraints(yearOfStudyInput, 1, 5);

        Label majorLabel = new Label("Major:");
        GridPane.setConstraints(majorLabel, 0, 6);
        TextField majorInput = new TextField();
        GridPane.setConstraints(majorInput, 1, 6);

        // Additional fields for teachers
        Label departmentLabel = new Label("Department:");
        GridPane.setConstraints(departmentLabel, 0, 5);
        TextField departmentInput = new TextField();
        GridPane.setConstraints(departmentInput, 1, 5);

        Label yearsOfExperienceLabel = new Label("Years of Experience:");
        GridPane.setConstraints(yearsOfExperienceLabel, 0, 6);
        TextField yearsOfExperienceInput = new TextField();
        GridPane.setConstraints(yearsOfExperienceInput, 1, 6);

        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 7);

        registerButton.setOnAction(e -> {
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            String email = emailInput.getText();
            String password = passwordInput.getText();
            String role = roleChoiceBox.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields");
                return;
            }

            String username = lastName + firstName + role + "@school.ac.za";

            try {
                if (role.equals("student")) {
                    String yearOfStudy = yearOfStudyInput.getText();
                    String major = majorInput.getText();
                    if (yearOfStudy.isEmpty() || major.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all fields for student");
                        return;
                    }
                    Student student = new Student(0, firstName, lastName, email, password,
                            Integer.parseInt(yearOfStudy), major);
                    Database.addUser(student, role);
                    showAlert(Alert.AlertType.INFORMATION, "Registration Successful!",
                            "Welcome, " + student.getFirstName() + ". Use this username to log in the system: "
                                    + username);
                } else if (role.equals("teacher")) {
                    String department = departmentInput.getText();
                    String yearsOfExperience = yearsOfExperienceInput.getText();
                    if (department.isEmpty() || yearsOfExperience.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Form Error!",
                                "Please enter all fields for teacher");
                        return;
                    }
                    Teacher teacher = new Teacher(0, firstName, lastName, email, password, department,
                            Integer.parseInt(yearsOfExperience));
                    Database.addUser(teacher, role);
                    showAlert(Alert.AlertType.INFORMATION, "Registration Successful!",
                            "Welcome, " + teacher.getFirstName());
                }
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Registration Error!",
                        "An error occurred: " + ex.getMessage());
            }
        });

        grid.getChildren().addAll(firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, emailLabel, emailInput, passwordLabel, passwordInput, roleLabel, roleChoiceBox, registerButton);

        roleChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("student")) {
                    grid.getChildren().removeAll(departmentLabel, departmentInput, yearsOfExperienceLabel, yearsOfExperienceInput);
                    grid.getChildren().addAll(yearOfStudyLabel, yearOfStudyInput, majorLabel, majorInput);
                } else if (newValue.equals("teacher")) {
                    grid.getChildren().removeAll(yearOfStudyLabel, yearOfStudyInput, majorLabel, majorInput);
                    grid.getChildren().addAll(departmentLabel, departmentInput, yearsOfExperienceLabel, yearsOfExperienceInput);
                }
            }
        });

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
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
