import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Database
{

    private static final String URL = "jdbc:mysql://localhost:3306/examsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "K@yl13n!07LGD";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addStudent(Student student) throws SQLException {
        String userQuery = "INSERT INTO users (first_name, last_name, email, password, role, username) " +
                            "VALUES (?, ?, ?, ?, 'Student', ?)";
        String studentQuery = "INSERT INTO students (user_id, year_of_study, major) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement studentStmt = conn.prepareStatement(studentQuery)) {
            userStmt.setString(1, student.getFirstName());
            userStmt.setString(2, student.getLastName());
            userStmt.setString(3, student.getEmail());
            userStmt.setString(4, student.getPassword());
            userStmt.setString(5, student.getUsername());
            userStmt.executeUpdate();

            ResultSet rs = userStmt.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                studentStmt.setInt(1, userId);
                studentStmt.setInt(2, student.getYearOfStudy());
                studentStmt.setString(3, student.getMajor());
                studentStmt.executeUpdate();
            }
        }
    }

    public static void addTeacher(Teacher teacher) throws SQLException {
        String userQuery = "INSERT INTO users (first_name, last_name, email, password, role, username) VALUES (?, ?, ?, ?, 'Teacher', ?)";
        String teacherQuery = "INSERT INTO teachers (user_id, department, years_of_experience) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement teacherStmt = conn.prepareStatement(teacherQuery)) {
            userStmt.setString(1, teacher.getFirstName());
            userStmt.setString(2, teacher.getLastName());
            userStmt.setString(3, teacher.getEmail());
            userStmt.setString(4, teacher.getPassword());
            userStmt.setString(5, teacher.getUsername());
            userStmt.executeUpdate();

            ResultSet rs = userStmt.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                teacherStmt.setInt(1, userId);
                teacherStmt.setString(2, teacher.getDepartment());
                teacherStmt.setInt(3, teacher.getYearsOfExperience());
                teacherStmt.executeUpdate();
            }
        }
    }

    public static boolean authenticateUser(String username, String password) throws SQLException
    {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query))
        {
            prepStatement.setString(1, username);
            prepStatement.setString(2, password);
            ResultSet resultSet = prepStatement.executeQuery();
            return resultSet.next();
        }
    }

    public static String getUserRole(String username) throws SQLException {
        String query = "SELECT role FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setString(1, username);
            ResultSet resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("role");
            } else {
                throw new SQLException("User role not found.");
            }
        }
    }

    public static List<String> getAvailableExams() throws SQLException {
        String query = "SELECT exam_id, title FROM exams";
        List<String> exams = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(query);
             ResultSet resultSet = prepStatement.executeQuery()) {
            while (resultSet.next()) {
                int examId = resultSet.getInt("exam_id");
                String title = resultSet.getString("title");
                exams.add(examId + ": " + title);
            }
        }
        return exams;
    }
}
