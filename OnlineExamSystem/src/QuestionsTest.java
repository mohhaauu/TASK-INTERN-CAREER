import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit test class for the Questions class.
 */
public class QuestionsTest
{

    /**
     * Test method to verify the getters and setters of the Questions class.
     */
    @Test
    public void testGettersAndSetters()
    {
        // Create a Questions object with initial values
        Questions question = new Questions(1, 401, "What is Java?",
                10, "A programming language");

        // Verify initial values using getters
        assertEquals(1, question.getId());
        assertEquals(401, question.getExamId());
        assertEquals("What is Java?", question.getQuestionText());
        assertEquals("A programming language", question.getCorrectAnswer());
        assertEquals(10, question.getMarks());

        // Update values using setters
        question.setId(2);
        question.setExamId(402);
        question.setQuestionText("What is JUnit?");
        question.setCorrectAnswer("A testing framework");
        question.setMarks(15);

        // Verify updated values using getters
        assertEquals(2, question.getId());
        assertEquals(402, question.getExamId());
        assertEquals("What is JUnit?", question.getQuestionText());
        assertEquals("A testing framework", question.getCorrectAnswer());
        assertEquals(15, question.getMarks());
    }
}
