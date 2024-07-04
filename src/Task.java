import java.time.LocalDate;

public class Task
{
    private int id;
    private String description;
    private LocalDate dueDate;
    private boolean isCompleted;

    // Constructors, getters, and setters

    public Task(int id, String description, LocalDate dueDate, boolean isCompleted) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    // default constructor.
    public Task(String description, LocalDate dueDate, boolean isCompleted)
    {
        this(-1, description, dueDate, isCompleted);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public LocalDate getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate = dueDate;
    }

    public boolean isCompleted()
    {
        return isCompleted;
    }

    public void setCompleted(boolean completed)
    {
        this.isCompleted = completed;
    }

    @Override
    public String toString()
    {
        return "task id: " + getId()  +
                "\ndescription: " + getDescription() +
                "\ndue date: " + getDueDate() +
                "\nCompleted:" + isCompleted();
    }
}
