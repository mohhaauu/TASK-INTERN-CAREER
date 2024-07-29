import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ExamTest {

    @Test
    public void testGettersAndSetters()
    {
        Exam exam = new Exam(1, 101, "Midyear Exam", 90);

        assertEquals(1, exam.getId());
        assertEquals(101, exam.getTeacherId());
        assertEquals("Midyear Exam", exam.getTitle());
        assertEquals(90, exam.getDuration());

        exam.setId(2);
        exam.setTeacherId(102);
        exam.setTitle("Final Exam");
        exam.setDuration(120);

        assertEquals(2, exam.getId());
        assertEquals(102, exam.getTeacherId());
        assertEquals("Final Exam", exam.getTitle());
        assertEquals(120, exam.getDuration());
    }
}
