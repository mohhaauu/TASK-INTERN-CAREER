import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ToDoListGUI extends Application
{
    private final ToDoList toDoList = new ToDoList();
    @Override
    public void start(Stage initialStage) {
        initialStage.setTitle("To-Do List Application");

        VBox vbox = new VBox();
        vbox.setSpacing(15);

        Button viewButton = new Button("View Tasks");
        Button addButton = new Button("Add Task");
        Button editButton = new Button("Edit Task");
        Button deleteButton = new Button("Delete Task");
        Button markCompletedButton = new Button("Mark Task as Completed");

        vbox.getChildren().addAll(addButton, viewButton, editButton, deleteButton, markCompletedButton);

        viewButton.setOnAction(e -> viewTasks());
        addButton.setOnAction(e -> addTask());
        editButton.setOnAction(e -> editTask());
        deleteButton.setOnAction(e -> deleteTask());
        markCompletedButton.setOnAction(e -> markTaskAsCompleted());

        Scene scene = new Scene(vbox, 300, 200);
        initialStage.setScene(scene);
        initialStage.show();
    }

    private void addTask() {
        // Implementation for adding a task
    }

    private void viewTasks() {
        // Implementation for viewing tasks
    }

    private void editTask() {
        // Implementation for editing a task
    }

    private void deleteTask() {
        // Implementation for deleting a task
    }

    private void markTaskAsCompleted() {
        // Implementation for marking a task as completed
    }

    public static void main(String[] args) {
        launch(args);
    }
}
