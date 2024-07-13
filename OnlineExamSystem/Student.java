public class Student extends User
{
    private int yearOfStudy;
    private String major;

    public Student(int id, String firstName, String lastName, String email, String password,
                   int yearOfStudy, String major)
    {
        super(id, firstName, lastName, email, password);
        this.yearOfStudy = yearOfStudy;
        this.major = major;
    }

    // Getters and setters

    public void setYearOfStudy(int yearOfStudy)
    {
        this.yearOfStudy = yearOfStudy;
    }

    public void setMajor(String major)
    {
        this.major = major;
    }

    public int getYearOfStudy()
    {
        return yearOfStudy;
    }

    public String getMajor()
    {
        return major;
    }

    public String toString()
    {
        return "First name: " + getFirstName() +
                "\nLast name: " + getLastName() +
                "\nEmail: " + getEmail() +
                "\nYear of study: " + getYearOfStudy() +
                "\nMajor: " + getMajor();
    }
}
