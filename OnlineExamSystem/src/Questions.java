public class Questions
{
    private final int id;
    private final String questionText;
    private final String questionType;
    private final String correctAnswer;

    public Questions(int id, String questionText, String questionType, String correctAnswer)
    {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
        this.correctAnswer = correctAnswer;
    }

    public int getId()
    {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
