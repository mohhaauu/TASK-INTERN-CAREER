import java.util.Date;
public class Task
{
    private String description;
    private Date dueDate;
    private boolean isCompleted;

    public Task(String description, Date dueDate) {
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    // Getters and Setters
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public boolean isCompleted()
    {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted)
    {
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString()
    {
        return "Task{" +
                "description ='" + description + '\'' +
                ", dueDate =" + dueDate +
                ", isCompleted =" + isCompleted +
                '}';
    }
}
