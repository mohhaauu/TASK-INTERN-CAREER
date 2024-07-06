import java.time.LocalDate;

public class Task
{
    // Private fields for task attributes
    private int id;
    private String description;
    private LocalDate dueDate;
    private boolean isCompleted;

    // Constructor to initialize all fields
    public Task(int id, String description, LocalDate dueDate, boolean isCompleted)
    {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    // Overloaded constructor for creating a Task without specifying an ID
    public Task(String description, LocalDate dueDate, boolean isCompleted)
    {
        this(-1, description, dueDate, isCompleted); // Calls the other constructor with a default ID of -1
    }

    // Getter for the ID
    public int getId()
    {
        return id;
    }

    // Setter for the ID
    public void setId(int id)
    {
        this.id = id;
    }

    // Getter for the description
    public String getDescription()
    {
        return description;
    }

    // Setter for the description
    public void setDescription(String description)
    {
        this.description = description;
    }

    // Getter for the due date
    public LocalDate getDueDate()
    {
        return dueDate;
    }

    // Setter for the due date
    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate = dueDate;
    }

    // Getter for the completed status
    public boolean isCompleted()
    {
        return isCompleted;
    }

    // Setter for the completed status
    public void setCompleted(boolean completed)
    {
        this.isCompleted = completed;
    }

    // Overriding the toString method to provide a custom string representation of a Task
    @Override
    public String toString()
    {
        return "task id: " + getId()  +
                "\ndescription: " + getDescription() +
                "\ndue date: " + getDueDate() +
                "\nCompleted:" + isCompleted();
    }
}
