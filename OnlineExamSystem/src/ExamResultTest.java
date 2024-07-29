import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit test class for the ExamResult class.
 */
public class ExamResultTest
{

    /**
     * Test method to verify the getters and setters of the ExamResult class.
     */
    @Test
    public void testGettersAndSetters()
    {
        // Create an ExamResult object with initial values
        ExamResult examResult = new ExamResult(201, 301, 85, "Good work!");

        // Verify initial values using getters
        assertEquals(201, examResult.getStudentId());
        assertEquals(301, examResult.getExamId());
        assertEquals(85, examResult.getTotalMarks());
        assertEquals("Good work!", examResult.getFeedback());

        // Update values using setters
        examResult.setId(1);
        examResult.setStudentId(202);
        examResult.setExamId(302);
        examResult.setTotalMarks(95);
        examResult.setFeedback("Excellent work!");

        // Verify updated values using getters
        assertEquals(1, examResult.getId());
        assertEquals(202, examResult.getStudentId());
        assertEquals(302, examResult.getExamId());
        assertEquals(95, examResult.getTotalMarks());
        assertEquals("Excellent work!", examResult.getFeedback());
    }
}
