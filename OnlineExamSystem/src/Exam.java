import java.util.List;

public class Exam {
    private int id;
    private String title;
    private int duration;
    private int teacherId;
    private List<Questions> questions;

    public Exam(int id, String title, int duration, int teacherId, List<Questions> questions) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.teacherId = teacherId;
        this.questions = questions;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public List<Questions> getQuestions() {
        return questions;
    }
}
