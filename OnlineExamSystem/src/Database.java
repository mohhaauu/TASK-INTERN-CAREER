import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database
{

    private static final String URL = "jdbc:mysql://localhost:3306/examsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "K@yl13n!07LGD";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Add the methods to insert Student and Teacher into the database
    public static void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO users (first_name, last_name, email, password, role, username," +
                        " year_of_study, major) VALUES (?, ?, ?, ?, 'Student', ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setString(1, student.getFirstName());
            prepStatement.setString(2, student.getLastName());
            prepStatement.setString(3, student.getEmail());
            prepStatement.setString(4, student.getPassword());
            prepStatement.setString(5, student.getUsername());
            prepStatement.setInt(6, student.getYearOfStudy());
            prepStatement.setString(7, student.getMajor());
            prepStatement.executeUpdate();
        }
    }

    public static void addTeacher(Teacher teacher) throws SQLException {
        String query = "INSERT INTO users (first_name, last_name, email, password, role, username, " +
                        "department, years_of_experience) VALUES (?, ?, ?, ?, 'Teacher', ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setString(1, teacher.getFirstName());
            prepStatement.setString(2, teacher.getLastName());
            prepStatement.setString(3, teacher.getEmail());
            prepStatement.setString(4, teacher.getPassword());
            prepStatement.setString(5, teacher.getUsername());
            prepStatement.setString(6, teacher.getDepartment());
            prepStatement.setInt(7, teacher.getYearsOfExperience());
            prepStatement.executeUpdate();
        }
    }

    public static boolean authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setString(1, username);
            prepStatement.setString(2, password);
            ResultSet resultSet = prepStatement.executeQuery();
            return resultSet.next();
        }
    }
}
