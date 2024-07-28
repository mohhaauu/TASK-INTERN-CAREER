public class Exam
{
    private int id;
    private int teacherId;
    private String title;
    private int duration;

    // Constructor
    public Exam(int id, int teacherId, String title, int duration)
    {
        this.id = id;
        this.teacherId = teacherId;
        this.title = title;
        this.duration = duration;
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

    public int getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(int teacherId)
    {
        this.teacherId = teacherId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }
}
