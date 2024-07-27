import java.util.Date;

public class StudentExam {
    private int id;
    private int studentId;
    private int examId;
    private Date startTime;
    private Date endTime;
    private String status;

    // Constructor
    public StudentExam(int studentId, int examId, Date startTime, Date endTime, String status) {
        this.studentId = studentId;
        this.examId = examId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }
    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
