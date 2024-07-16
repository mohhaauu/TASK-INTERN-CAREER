public abstract class User
{
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;

    public User(int id, String firstName, String lastName, String email, String password)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public void setId(int id)
    {
        this.id = id;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public int getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }
}
