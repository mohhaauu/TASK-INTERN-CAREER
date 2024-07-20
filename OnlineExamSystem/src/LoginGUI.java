import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginGUI extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("User Login");

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // UI components for login
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");

        vbox.getChildren().addAll(usernameField, passwordField, loginButton, createAccountButton);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            try {
                if (Database.authenticateUser(username, password)) {
                    showAlert("Login successful!");

                    String role = Database.getUserRole(username); // Get user role from the database
                    if (role.equals("Student")) {
                        ExamTaking examTaking = new ExamTaking(username);
                        examTaking.start(new Stage());
                    } else if (role.equals("Teacher")) {
                        ExamCreation examCreation = new ExamCreation(username);
                        examCreation.start(new Stage());
                    }
                    primaryStage.close();
                } else {
                    showAlert("Invalid username or password.");
                }
            } catch (Exception ex) {
                showAlert("Error during login: " + ex.getMessage());
            }
        });

        createAccountButton.setOnAction(e -> {
            UserRegistrationGUI registrationGUI = new UserRegistrationGUI();
            try {
                registrationGUI.start(new Stage());
                primaryStage.close(); // Close the current login window
            } catch (Exception ex) {
                showAlert("Error opening registration: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
