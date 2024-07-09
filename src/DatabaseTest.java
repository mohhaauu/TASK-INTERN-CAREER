import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.*;

/**
 * This class contains JUnit tests for the {@link Database} class.
 * It tests the methods for adding, retrieving, updating, and deleting tasks from the database.
 */
public class DatabaseTest
{

    /**
     * Sets up the database before any tests run by clearing the tasks table.
     * This method is run once before all tests.
     *
     * @throws SQLException if a database access error occurs.
     */
    @BeforeClass
    public static void setupDatabase() throws SQLException
    {
        Database.clearTasksTable();
    }

    /**
     * Clears the tasks table before each test.
     * This method is run before each test.
     *
     * @throws SQLException if a database access error occurs.
     */
    @Before
    public void clearDatabase() throws SQLException
    {
        Database.clearTasksTable();
    }

    /**
     * Tests the {@code addTask} method of the {@link Database} class.
     * Ensures that a task is added to the database correctly.
     *
     * @throws SQLException if a database access error occurs.
     */
    @Test
    public void testAddTask() throws SQLException
    {
        Task task = new Task("Test Task", LocalDate.now(), false);
        Database.addTask(task);
        List<Task> tasks = Database.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.getFirst().getDescription());
    }

    /**
     * Tests the {@code getTasks} method of the {@link Database} class.
     * Ensures that tasks are retrieved from the database correctly.
     *
     * @throws SQLException if a database access error occurs.
     */
    @Test
    public void testGetTasks() throws SQLException
    {
        Task task1 = new Task("Test Task 1", LocalDate.now(), false);
        Task task2 = new Task("Test Task 2", LocalDate.now().plusDays(1), true);
        Database.addTask(task1);
        Database.addTask(task2);
        List<Task> tasks = Database.getTasks();
        assertEquals(2, tasks.size());
    }

    /**
     * Tests the {@code updateTask} method of the {@link Database} class.
     * Ensures that a task is updated in the database correctly.
     *
     * @throws SQLException if a database access error occurs.
     */
    @Test
    public void testUpdateTask() throws SQLException
    {
        Task task = new Task("Test Task", LocalDate.now(), false);
        Database.addTask(task);
        task.setDescription("Updated Task");
        Database.updateTask(task);
        List<Task> tasks = Database.getTasks();
        assertEquals("Updated Task", tasks.getFirst().getDescription());
    }

    /**
     * Tests the {@code deleteTask} method of the {@link Database} class.
     * Ensures that a task is deleted from the database correctly.
     *
     * @throws SQLException if a database access error occurs.
     */
    @Test
    public void testDeleteTask() throws SQLException
    {
        Task task = new Task("Test Task", LocalDate.now(), false);
        Database.addTask(task);
        Database.deleteTask(task);
        List<Task> tasks = Database.getTasks();
        assertTrue(tasks.isEmpty());
    }
}
