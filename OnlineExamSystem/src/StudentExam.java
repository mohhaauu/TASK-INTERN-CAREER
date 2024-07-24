import java.util.List;

public class StudentExam {
    private int studentId;
    private int examId;
    private List<Questions> questions;
    private int score;

    // Constructor
    public StudentExam(int studentId, int examId, List<Questions> questions) {
        this.studentId = studentId;
        this.examId = examId;
        this.questions = questions;
    }

    // Getters and setters
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

    public List<Questions> getQuestions()
    {
        return questions;
    }
    public void setQuestions(List<Questions> questions)
    {
        this.questions = questions;
    }

    public int getScore()
    {
        return score;
    }
    public void setScore(int score)
    {
        this.score = score;
    }
}
