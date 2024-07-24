import javafx.application.Application;
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

        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 5);

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
                User user = new User(0, firstName, lastName, email, password, role);
                Database.addUser(user);
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful!",
                        "Welcome, " + user.getFirstName() + ". Use this username to log in the system: " + username);
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Registration Error!", "An error occurred: " + ex.getMessage());
            }
        });

        grid.getChildren().addAll(firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, emailLabel, emailInput, passwordLabel, passwordInput, roleLabel, roleChoiceBox, registerButton);

        Scene scene = new Scene(grid, 300, 250);
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
