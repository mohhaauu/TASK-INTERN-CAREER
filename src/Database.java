import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods to interact with the tasks database.
 */
public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/ToDoListDB";
    private static final String USER = "root"; // Change to your database username
    private static final String PASSWORD = "K@yl13n!07LGD"; // Change to your database password

    /**
     * Establishes a connection to the database using JDBC.
     *
     * @return a connection to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Inserts a new task into the tasks table.
     *
     * @param task the task to add
     * @throws SQLException if a database access error occurs
     */
    public static void addTask(Task task) throws SQLException {
        String query = "INSERT INTO tasks (description, due_date, completed) VALUES (?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStatement.setString(1, task.getDescription());
            prepStatement.setDate(2, java.sql.Date.valueOf(task.getDueDate()));
            prepStatement.setBoolean(3, task.isCompleted());
            prepStatement.executeUpdate();

            // Retrieves the auto-generated key (task_id) and updates the Task object with it
            try (ResultSet generatedKeys = prepStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    /**
     * Retrieves all tasks from the tasks table in the database.
     *
     * @return a list of tasks
     * @throws SQLException if a database access error occurs
     */
    public static List<Task> getTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query);
             ResultSet rs = prepStatement.executeQuery()) {
            while (rs.next()) {
                // Creates Task objects from the retrieved data and adds them to the tasks list
                Task task = new Task(rs.getInt("task_id"), rs.getString("description"),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getBoolean("completed"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Updates an existing task in the tasks table.
     *
     * @param task the task to update
     * @throws SQLException if a database access error occurs
     */
    public static void updateTask(Task task) throws SQLException {
        String query = "UPDATE tasks SET description = ?, due_date = ?, completed = ? WHERE task_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setString(1, task.getDescription());
            prepStatement.setDate(2, java.sql.Date.valueOf(task.getDueDate()));
            prepStatement.setBoolean(3, task.isCompleted());
            prepStatement.setInt(4, task.getId());
            prepStatement.executeUpdate();
        }
    }

    /**
     * Deletes a task from the tasks table.
     *
     * @param task the task to delete
     * @throws SQLException if a database access error occurs
     */
    public static void deleteTask(Task task) throws SQLException {
        String query = "DELETE FROM tasks WHERE task_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setInt(1, task.getId());
            prepStatement.executeUpdate();
        }
        // After deletion, renumbers the task_ids to ensure sequential ordering
        renumberTaskIds();
    }

    /**
     * Renumbers the task_ids sequentially after a deletion.
     *
     * @throws SQLException if a database access error occurs
     */
    public static void renumberTaskIds() throws SQLException {
        String selectQuery = "SELECT task_id FROM tasks ORDER BY task_id";
        String updateQuery = "UPDATE tasks SET task_id = ? WHERE task_id = ?";
        List<Integer> taskIds = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
             ResultSet rs = selectStatement.executeQuery()) {
            while (rs.next()) {
                taskIds.add(rs.getInt("task_id"));
            }
        }

        try (Connection conn = getConnection();
             PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
            for (int i = 0; i < taskIds.size(); i++) {
                updateStatement.setInt(1, i + 1);
                updateStatement.setInt(2, taskIds.get(i));
                updateStatement.executeUpdate();
            }
        }

        // Resets the auto-increment counter for task_id after renumbering
        resetAutoIncrement();
    }

    /**
     * Resets the auto-increment counter for task_id to 1.
     *
     * @throws SQLException if a database access error occurs
     */
    public static void resetAutoIncrement() throws SQLException {
        String query = "ALTER TABLE tasks AUTO_INCREMENT = 1";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.executeUpdate();
        }
    }

    /**
     * Checks if the tasks table is empty.
     *
     * @return {@code true} if the tasks table is empty, {@code false} otherwise
     * @throws SQLException if a database access error occurs
     */
    public static boolean isTasksTableEmpty() throws SQLException {
        String query = "SELECT COUNT(*) FROM tasks";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query);
             ResultSet rs = prepStatement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return false;
    }

/**
     * Clears all tasks from the tasks table in the database.
     *
     * @throws SQLException if a database access error occurs
     */
    public static void clearTasksTable() throws SQLException {
        String query = "DELETE FROM tasks";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.executeUpdate();
        }
    }
}
