import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;

/**
 * This class contains JUnit tests for the {@link Task} class.
 * It tests the getter and setter methods as well as the {@code toString} method of the {@link Task} class.
 */
public class TestApp
{

    private Task task;

    /**
     * Initializes a {@link Task} object before each test.
     * This method is run before each test.
     */
    @Before
    public void setUp()
    {
        task = new Task(1, "Test Task", LocalDate.of(2023, 7, 1), false);
    }

    /**
     * Tests the {@code getId} method of the {@link Task} class.
     * Ensures that the ID of the task is retrieved correctly.
     */
    @Test
    public void testGetId()
    {
        assertEquals(1, task.getId());
    }

    /**
     * Tests the {@code setId} method of the {@link Task} class.
     * Ensures that the ID of the task is set correctly.
     */
    @Test
    public void testSetId()
    {
        task.setId(2);
        assertEquals(2, task.getId());
    }

    /**
     * Tests the {@code getDescription} method of the {@link Task} class.
     * Ensures that the description of the task is retrieved correctly.
     */
    @Test
    public void testGetDescription()
    {
        assertEquals("Test Task", task.getDescription());
    }

    /**
     * Tests the {@code setDescription} method of the {@link Task} class.
     * Ensures that the description of the task is set correctly.
     */
    @Test
    public void testSetDescription()
    {
        task.setDescription("Updated Task");
        assertEquals("Updated Task", task.getDescription());
    }

    /**
     * Tests the {@code getDueDate} method of the {@link Task} class.
     * Ensures that the due date of the task is retrieved correctly.
     */
    @Test
    public void testGetDueDate()
    {
        assertEquals(LocalDate.of(2023, 7, 1), task.getDueDate());
    }

    /**
     * Tests the {@code setDueDate} method of the {@link Task} class.
     * Ensures that the due date of the task is set correctly.
     */
    @Test
    public void testSetDueDate()
    {
        LocalDate newDate = LocalDate.of(2023, 8, 1);
        task.setDueDate(newDate);
        assertEquals(newDate, task.getDueDate());
    }

    /**
     * Tests the {@code isCompleted} method of the {@link Task} class.
     * Ensures that the completion status of the task is retrieved correctly.
     */
    @Test
    public void testIsCompleted()
    {
        assertFalse(task.isCompleted());
    }

    /**
     * Tests the {@code setCompleted} method of the {@link Task} class.
     * Ensures that the completion status of the task is set correctly.
     */
    @Test
    public void testSetCompleted()
    {
        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    /**
     * Tests the {@code toString} method of the {@link Task} class.
     * Ensures that the string representation of the task is generated correctly.
     */
    @Test
    public void testToString()
    {
        String expectedString = "task id: 1\nDescription: Test Task\nDue date: 2023-07-01\nCompleted:false";
        assertEquals(expectedString, task.toString());
    }
}
