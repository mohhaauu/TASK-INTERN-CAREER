/**
 * This class represents an exam in the system.
 */
public class Exam
{
    private int examId;
    private int teacherId;
    private String title;
    private int duration;

    /**
     * Constructor to initialize an exam.
     *
     * @param examId The ID of the exam.
     * @param teacherId The ID of the teacher creating the exam.
     * @param title The title of the exam.
     * @param duration The duration of the exam in minutes.
     */
    public Exam(int examId, int teacherId, String title, int duration)
    {
        this.examId = examId;
        this.teacherId = teacherId;
        this.title = title;
        this.duration = duration;
    }

    // Getters and setters

    public int getExamId()
    {
        return examId;
    }

    public void setExamId(int examId)
    {
        this.examId = examId;
    }

    public int getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(int teacherId)
    {
        this.teacherId = teacherId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }
}
