import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit test class for the Exam class.
 */
public class ExamTest
{

    /**
     * Test method to verify the getters and setters of the Exam class.
     */
    @Test
    public void testGettersAndSetters()
    {
        // Create an Exam object with initial values
        Exam exam = new Exam(1, 101, "Midyear Exam", 90);

        // Verify initial values using getters
        assertEquals(1, exam.getExamId());
        assertEquals(101, exam.getTeacherId());
        assertEquals("Midyear Exam", exam.getTitle());
        assertEquals(90, exam.getDuration());

        // Update values using setters
        exam.setExamId(2);
        exam.setTeacherId(102);
        exam.setTitle("Final Exam");
        exam.setDuration(120);

        // Verify updated values using getters
        assertEquals(2, exam.getExamId());
        assertEquals(102, exam.getTeacherId());
        assertEquals("Final Exam", exam.getTitle());
        assertEquals(120, exam.getDuration());
    }
}
