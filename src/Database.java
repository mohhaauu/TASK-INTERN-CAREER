import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database
{
    private static final String URL = "jdbc:mysql://localhost:3306/ToDoListDB";
    private static final String USER = "root"; // Change to your database username
    private static final String PASSWORD = "K@yl13n!07LGD"; // Change to your database password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addTask(Task task) throws SQLException
    {
        String query = "INSERT INTO tasks (description, due_date, completed) VALUES (?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement prepStatement = conn.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS))
        {
            prepStatement.setString(1, task.getDescription());
            prepStatement.setDate(2, java.sql.Date.valueOf(task.getDueDate()));
            prepStatement.setBoolean(3, task.isCompleted());
            prepStatement.executeUpdate();

            try (ResultSet generatedKeys = prepStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1)); // Update task object with the generated ID
                }
            }
        }
    }

    public static List<Task> getTasks() throws SQLException
    {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (Connection conn = getConnection(); PreparedStatement prepStatement = conn.prepareStatement(query);
             ResultSet rs = prepStatement.executeQuery()) {
            while (rs.next()) {
                Task task = new Task(rs.getString("description"),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getBoolean("completed")
                );
                tasks.add(task);
            }
        }
        return tasks;
    }

    public static void updateTask(Task task) throws SQLException
    {
        String query = "UPDATE tasks SET description = " + task.getDescription() + "WHERE task_id = " + task.getId();
        try (Connection conn = getConnection(); PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setString(1, task.getDescription());
            prepStatement.setDate(2, java.sql.Date.valueOf(task.getDueDate()));
            prepStatement.setBoolean(3, task.isCompleted());
            prepStatement.setInt(4, task.getId());
            prepStatement.executeUpdate();
        }
    }

    public static void deleteTask(Task task) throws SQLException
    {
        String query = "DELETE FROM tasks WHERE task_id = " + task.getId();
        try (Connection conn = getConnection(); PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setInt(1, task.getId());
            prepStatement.executeUpdate();
        }
    }
}
