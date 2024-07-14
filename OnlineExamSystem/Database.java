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

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
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
