import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class ToDoList
{
    private final List<Task> tasks;

    public ToDoList() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void viewTasks() {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void editTask(int index, String newDescription, Date newDueDate) {
        Task task = tasks.get(index);
        task.setDescription(newDescription);
        task.setDueDate(newDueDate);
    }

    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public void markTaskAsCompleted(int index) {
        Task task = tasks.get(index);
        task.setCompleted(true);
    }

    public void sortTasksByDueDate() {
        tasks.sort((task1, task2) -> task1.getDueDate().compareTo(task2.getDueDate()));
    }

    public void sortTasksByCompletionStatus() {
        tasks.sort((task1, task2) -> Boolean.compare(task1.isCompleted(), task2.isCompleted()));
    }
}
