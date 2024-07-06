import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class ToDoListGUI extends Application
{

    // ObservableList to hold tasks, enabling automatic UI updates
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("To-Do List Application");

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // UI components: ListView for tasks, input fields, checkboxes, buttons
        ListView<Task> listView = new ListView<>(tasks);
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Task Description");
        DatePicker dueDatePicker = new DatePicker();
        CheckBox completedCheckBox = new CheckBox("Completed");

        Button viewButton = new Button("View Task");
        Button addButton = new Button("Add Task");
        Button updateButton = new Button("Edit Task");
        Button deleteButton = new Button("Delete Task");

        vbox.getChildren().addAll(listView, descriptionField, dueDatePicker, completedCheckBox,
                viewButton, addButton, updateButton, deleteButton);

        // Event handler for viewButton: Fetches and displays all tasks from the database
        viewButton.setOnAction(e ->
        {
            try
            {
                tasks.clear(); // Clear current list of tasks
                tasks.addAll(Database.getTasks()); // Fetch tasks from database and add to observable list
                listView.setItems(tasks); // Update ListView with fetched tasks
            }
            catch (SQLException ex)
            {
                showAlert("Error fetching tasks: " + ex.getMessage());
            }
        });

        // Event handler for addButton: Adds a new task to the database and updates the UI
        addButton.setOnAction(e ->
        {
            String description = descriptionField.getText();
            LocalDate dueDate = dueDatePicker.getValue();
            boolean isCompleted = completedCheckBox.isSelected();
            if (description != null && !description.isEmpty() && dueDate != null)
            {
                Task task = new Task(description, dueDate, isCompleted);
                try
                {
                    if (Database.isTasksTableEmpty())
                    {
                        Database.resetAutoIncrement(); // Reset auto-increment if the tasks table is empty
                    }
                    Database.addTask(task); // Add task to the database
                    tasks.add(task); // Add the new task to the observable list
                    clearFields(descriptionField, dueDatePicker, completedCheckBox); // Clear input fields
                }
                catch (SQLException ex)
                {
                    showAlert("Error adding task: " + ex.getMessage());
                }
            }
            else
            {
                showAlert("Please fill in all fields.");
            }
        });

        // Event handler for updateButton: Updates an existing task in the database and refreshes the UI
        updateButton.setOnAction(e ->
        {
            Task selectedTask = listView.getSelectionModel().getSelectedItem();
            if (selectedTask != null)
            {
                selectedTask.setDescription(descriptionField.getText());
                selectedTask.setDueDate(dueDatePicker.getValue());
                selectedTask.setCompleted(completedCheckBox.isSelected());
                try
                {
                    Database.updateTask(selectedTask); // Update task in the database
                    listView.refresh(); // Refresh ListView to reflect changes
                    clearFields(descriptionField, dueDatePicker, completedCheckBox); // Clear input fields
                }
                catch (SQLException ex)
                {
                    showAlert("Error updating task: " + ex.getMessage());
                }
            }
            else
            {
                showAlert("Please select a task to edit.");
            }
        });

        // Event handler for deleteButton: Deletes a task from the database and refreshes the UI
        deleteButton.setOnAction(e ->
        {
            Task selectedTask = listView.getSelectionModel().getSelectedItem();
            if (selectedTask != null)
            {
                try
                {
                    Database.deleteTask(selectedTask); // Delete task from the database
                    tasks.remove(selectedTask); // Remove task from observable list
                    Database.renumberTaskIds(); // Renumber task IDs after deletion
                    tasks.clear(); // Clear current list of tasks
                    tasks.addAll(Database.getTasks()); // Refresh the task list from the database
                } catch (SQLException ex)
                {
                    showAlert("Error deleting task: " + ex.getMessage());
                }
            }
            else
            {
                showAlert("Please select a task to delete.");
            }
        });

        // Listener for selection changes in the ListView: Updates input fields with selected task details
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            if (newSelection != null)
            {
                descriptionField.setText(newSelection.getDescription());
                dueDatePicker.setValue(newSelection.getDueDate());
                completedCheckBox.setSelected(newSelection.isCompleted());
            }
        });

        // Create the main scene and display the stage
        Scene scene = new Scene(vbox, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to clear input fields
    private void clearFields(TextField descriptionField, DatePicker dueDatePicker, CheckBox completedCheckBox)
    {
        descriptionField.clear();
        dueDatePicker.setValue(null);
        completedCheckBox.setSelected(false);
    }

    // Helper method to display an alert dialog with a message
    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Details");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Entry point of the application
    public static void main(String[] args)
    {
        launch(args);
    }
}
