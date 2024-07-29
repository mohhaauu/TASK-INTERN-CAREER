/**
 * This class represents the result of an exam taken by a student.
 */
public class ExamResult
{
    private int id;
    private int studentId;
    private int examId;
    private int totalMarks;
    private String feedback;

    /**
     * Constructor to initialize an exam result.
     *
     * @param studentId The ID of the student.
     * @param examId The ID of the exam.
     * @param totalMarks The total marks obtained by the student.
     * @param feedback The feedback given to the student.
     */
    public ExamResult(int studentId, int examId, int totalMarks, String feedback)
    {
        this.studentId = studentId;
        this.examId = examId;
        this.totalMarks = totalMarks;
        this.feedback = feedback;
    }

    // Getters and setters

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    public int getExamId()
    {
        return examId;
    }

    public void setExamId(int examId)
    {
        this.examId = examId;
    }

    public int getTotalMarks()
    {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks)
    {
        this.totalMarks = totalMarks;
    }

    public String getFeedback()
    {
        return feedback;
    }

    public void setFeedback(String feedback)
    {
        this.feedback = feedback;
    }
}
