import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDate;

public class ToDoListGUI extends Application {
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    //private TableView<Task> table;

    @Override
    public void start(Stage primeStage) {
        primeStage.setTitle("To-Do List Application");

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        ListView<Task> listView = new ListView<>(tasks);
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Task Description");
        DatePicker dueDatePicker = new DatePicker();
        CheckBox completedCheckBox = new CheckBox("Completed");

        Button viewButton = new Button("View Task");
        Button addButton = new Button("Add Task");
        Button updateButton = new Button("Edit Task");
        Button deleteButton = new Button("Delete Task");
        Button markCompletedButton = new Button("Mark Task as Completed");

        vbox.getChildren().addAll(listView, descriptionField, dueDatePicker, completedCheckBox, viewButton, addButton,
                updateButton, deleteButton, markCompletedButton);

        // Clear the listView at the start
        tasks.clear();

        viewButton.setOnAction(e -> {
            try {
                tasks.clear();
                tasks.addAll(Database.getTasks());
                listView.setItems(tasks);
            } catch (SQLException ex) {
                showAlert("Error fetching tasks: " + ex.getMessage());
            }
        });

        addButton.setOnAction(e -> {
            String description = descriptionField.getText();
            LocalDate dueDate = dueDatePicker.getValue();
            boolean isCompleted = completedCheckBox.isSelected();
            if (description != null && !description.isEmpty() && dueDate != null) {
                Task task = new Task(description, dueDate, isCompleted);
                try {
                    Database.addTask(task);
                    clearFields(descriptionField, dueDatePicker, completedCheckBox);
                } catch (SQLException ex) {
                    showAlert("Error adding task: " + ex.getMessage());
                }
            } else {
                showAlert("Please fill in all fields.");
            }
        });

        updateButton.setOnAction(e -> {
            Task selectedTask = listView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                selectedTask.setDescription(descriptionField.getText());
                selectedTask.setDueDate(dueDatePicker.getValue());
                selectedTask.setCompleted(completedCheckBox.isSelected());
                try {
                    Database.updateTask(selectedTask);
                    listView.refresh();
                    clearFields(descriptionField, dueDatePicker, completedCheckBox);
                } catch (SQLException ex) {
                    showAlert("Error updating task: " + ex.getMessage());
                }
            } else {
                showAlert("Please select a task to edit.");
            }
        });

        deleteButton.setOnAction(e -> {
            Task selectedTask = listView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                try {
                    Database.deleteTask(selectedTask);
                    tasks.remove(selectedTask);
                } catch (SQLException ex) {
                    showAlert("Error deleting task: " + ex.getMessage());
                }
            } else {
                showAlert("Please select a task to delete.");
            }
        });

        markCompletedButton.setOnAction(e -> {
            Task selectedTask = listView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                selectedTask.setCompleted(true);
                try {
                    Database.updateTask(selectedTask);
                    listView.refresh();
                } catch (SQLException ex) {
                    showAlert("Error marking task as completed: " + ex.getMessage());
                }
            } else {
                showAlert("Please select a task to mark as completed.");
            }
        });


        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                descriptionField.setText(newSelection.getDescription());
                dueDatePicker.setValue(newSelection.getDueDate());
                completedCheckBox.setSelected(newSelection.isCompleted());
            }
        });

        Scene scene = new Scene(vbox, 600, 500);
        primeStage.setScene(scene);
        primeStage.show();
    }

    private void clearFields(TextField descriptionField, DatePicker dueDatePicker, CheckBox completedCheckBox) {
        descriptionField.clear();
        dueDatePicker.setValue(null);
        completedCheckBox.setSelected(false);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Details");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
