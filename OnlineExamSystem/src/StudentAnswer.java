public class StudentAnswer {
    private int id;
    private int studentId;
    private int questionId;
    private String answer;
    private int mark;

    // Constructor
    public StudentAnswer(int studentId, int questionId, String answer, int mark) {
        this.studentId = studentId;
        this.questionId = questionId;
        this.answer = answer;
        this.mark = mark;
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

    public int getQuestionId()
    {
        return questionId;
    }
    public void setQuestionId(int questionId)
    {
        this.questionId = questionId;
    }

    public String getAnswer()
    {
        return answer;
    }
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public int getMark()
    {
        return mark;
    }
    public void setMark(int mark)
    {
        this.mark = mark;
    }
}
