import java.time.LocalDate;

/**
 * Represents a task with a description, due date, and completion status.
 */
public class Task
{
    // Private fields for task attributes
    private int id;
    private String description;
    private LocalDate dueDate;
    private boolean isCompleted;

    /**
     * Constructs a Task with the specified id, description, due date, and completion status.
     *
     * @param id          the ID of the task
     * @param description the description of the task
     * @param dueDate     the due date of the task
     * @param isCompleted the completion status of the task
     */
    public Task(int id, String description, LocalDate dueDate, boolean isCompleted)
    {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    /**
     * Constructs a Task with the specified description, due date, and completion status.
     * The ID is set to -1 by default.
     *
     * @param description the description of the task
     * @param dueDate     the due date of the task
     * @param isCompleted the completion status of the task
     */
    public Task(String description, LocalDate dueDate, boolean isCompleted)
    {
        this(-1, description, dueDate, isCompleted); // Calls the other constructor with a default ID of -1
    }

    /**
     * Returns the ID of the task.
     *
     * @return the ID of the task
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the ID of the task.
     *
     * @param id the ID to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Returns the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns the due date of the task.
     *
     * @return the due date of the task
     */
    public LocalDate getDueDate()
    {
        return dueDate;
    }

    /**
     * Sets the due date of the task.
     *
     * @param dueDate the due date to set
     */
    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate = dueDate;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return {@code true} if the task is completed, {@code false} otherwise
     */
    public boolean isCompleted()
    {
        return isCompleted;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param completed the completion status to set
     */
    public void setCompleted(boolean completed)
    {
        this.isCompleted = completed;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a string representation of the task
     */
    @Override
    public String toString()
    {
        return "task id: " + getId() +
                "\nDescription: " + getDescription() +
                "\nDue date: " + getDueDate() +
                "\nCompleted:" + isCompleted();
    }
}
