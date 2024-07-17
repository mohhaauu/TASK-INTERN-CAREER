public class Teacher extends User
{
    private String department;
    private int yearsOfExperience;

    // default constructor
    public Teacher(int id, String firstName, String lastName, String email, String password,
                   String department, int yearsOfExperience)
    {
        super(id, firstName, lastName, email, password);
        this.department = department;
        this.yearsOfExperience = yearsOfExperience;
    }

    // constructor without id
    public Teacher(String firstName, String lastName, String email, String password,
                   String username, String department, int yearsOfExperience)
    {
        super(0, firstName, lastName, email, password);
        this.setUsername(username);
        this.department = department;
        this.yearsOfExperience = yearsOfExperience;
    }

// Getters and setters
    public void setDepartment(String department)
    {
        this.department = department;
    }

    public void setYearsOfExperience(int yearsOfExperience)
    {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getDepartment()
    {
        return department;
    }

    public int getYearsOfExperience()
    {
        return yearsOfExperience;
    }

    public String toString()
    {
        return "First name: " + getFirstName() +
                "\nLast name: " + getLastName() +
                "\nEmail: " + getEmail() +
                "\nDepartment: " + getDepartment() +
                "\nYears of experience: " + getYearsOfExperience();
    }
}
