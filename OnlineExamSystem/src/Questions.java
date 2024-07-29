/**
 * This class represents a question in an exam.
 */
public class Questions
{
    private int id;
    private int examId;
    private String questionText;
    private int marks;
    private String correctAnswer;

    /**
     * Constructor to initialize a question.
     *
     * @param examId The ID of the exam to which this question belongs.
     * @param questionText The text of the question.
     * @param marks The marks allocated to this question.
     * @param correctAnswer The correct answer to the question.
     */
    public Questions(int examId, String questionText, int marks, String correctAnswer)
    {
        this.examId = examId;
        this.questionText = questionText;
        this.marks = marks;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Constructor to initialize a question with ID.
     *
     * @param id The ID of the question.
     * @param examId The ID of the exam to which this question belongs.
     * @param questionText The text of the question.
     * @param marks The marks allocated to this question.
     * @param correctAnswer The correct answer to the question.
     */
    public Questions(int id, int examId, String questionText, int marks, String correctAnswer)
    {
        this.id = id;
        this.examId = examId;
        this.questionText = questionText;
        this.marks = marks;
        this.correctAnswer = correctAnswer;
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

    public int getExamId()
    {
        return examId;
    }

    public void setExamId(int examId)
    {
        this.examId = examId;
    }

    public String getQuestionText()
    {
        return questionText;
    }

    public void setQuestionText(String questionText)
    {
        this.questionText = questionText;
    }

    public int getMarks()
    {
        return marks;
    }

    public void setMarks(int marks)
    {
        this.marks = marks;
    }

    public String getCorrectAnswer()
    {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer)
    {
        this.correctAnswer = correctAnswer;
    }
}
