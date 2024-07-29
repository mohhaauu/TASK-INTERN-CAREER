import java.util.Date;

/**
 * Represents a student's exam record with details about the exam instance.
 */
public class StudentExam
{
    private int id;
    private int studentId;
    private int examId;
    private Date startTime;
    private Date endTime;
    private String status;

    /**
     * Constructs a new StudentExam with the specified details.
     *
     * @param studentId the ID of the student taking the exam
     * @param examId the ID of the exam
     * @param startTime the start time of the exam
     * @param endTime the end time of the exam
     * @param status the status of the exam (e.g., completed, in progress)
     */
    public StudentExam(int studentId, int examId, Date startTime, Date endTime, String status) {
        this.studentId = studentId;
        this.examId = examId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    /**
     * Returns the unique identifier of this exam record.
     *
     * @return the ID of this exam record
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the unique identifier for this exam record.
     *
     * @param id the ID to set for this exam record
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Returns the ID of the student taking the exam.
     *
     * @return the student ID
     */
    public int getStudentId()
    {
        return studentId;
    }

    /**
     * Sets the ID of the student taking the exam.
     *
     * @param studentId the student ID to set
     */
    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    /**
     * Returns the ID of the exam.
     *
     * @return the exam ID
     */
    public int getExamId()
    {
        return examId;
    }

    /**
     * Sets the ID of the exam.
     *
     * @param examId the exam ID to set
     */
    public void setExamId(int examId)
    {
        this.examId = examId;
    }

    /**
     * Returns the start time of the exam.
     *
     * @return the start time of the exam
     */
    public Date getStartTime()
    {
        return startTime;
    }

    /**
     * Sets the start time of the exam.
     *
     * @param startTime the start time to set
     */
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of the exam.
     *
     * @return the end time of the exam
     */
    public Date getEndTime()
    {
        return endTime;
    }

    /**
     * Sets the end time of the exam.
     *
     * @param endTime the end time to set
     */
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    /**
     * Returns the status of the exam.
     *
     * @return the status of the exam
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Sets the status of the exam.
     *
     * @param status the status to set
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
}
