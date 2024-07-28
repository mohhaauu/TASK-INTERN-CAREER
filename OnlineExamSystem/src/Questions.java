public class Questions
{
    private int id;
    private int examId;
    private String questionText;
    private String correctAnswer;
    private int marks;

    public Questions(int id, int examId, String questionText, String correctAnswer, int marks) {
        this.id = id;
        this.examId = examId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.marks = marks;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
}
