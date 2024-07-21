public class Questions {
    private int id;
    private String questionText;
    private String correctAnswer;
    private String type; // "multiple-choice", "true-false", "short-answer"

    public Questions(int id, String questionText, String correctAnswer, String type)
    {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.type = type;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText)
    {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
