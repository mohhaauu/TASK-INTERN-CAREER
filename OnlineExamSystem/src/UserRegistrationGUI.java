import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.SQLException;

/**
 * The UserRegistrationGUI class provides a JavaFX application for user registration.
 * This class allows users to enter their details and register as either a student or a teacher.
 */
public class UserRegistrationGUI extends Application
{
    /**
     * Starts the JavaFX application, sets up the user interface, and handles user interactions.
     *
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage)
    {
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

            try
            {
                User user = new User(0, firstName, lastName, email, password, role);
                Database.addUser(user);
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful!",
                        "Welcome, " + user.getFirstName() + ". Use your email and password to login.");
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.start(new Stage());
            } catch (SQLException ex)
            {
                showAlert(Alert.AlertType.ERROR, "Registration Error!", "An error occurred: " + ex.getMessage());
            }
        });

        grid.getChildren().addAll(firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, emailLabel, emailInput, passwordLabel, passwordInput, roleLabel, roleChoiceBox, registerButton);

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows an alert dialog with the specified type, title, and message.
     *
     * @param alertType The type of alert to display.
     * @param title The title of the alert dialog.
     * @param message The message to display in the alert dialog.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
