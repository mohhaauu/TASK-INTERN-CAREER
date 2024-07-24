import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/online_exam_system";
    private static final String USER = "root";
    private static final String PASSWORD = "K@yl13n!07LGD";

    public static void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static User validateLogin(String email, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String role = rs.getString("role");
                    return new User(id, firstName, lastName, email, password, role);
                }
            }
        }
        return null;
    }

    public static void addExam(Exam exam) throws SQLException {
        String query = "INSERT INTO exams (teacher_id, title, duration) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, exam.getTeacherId());
            stmt.setString(2, exam.getTitle());
            stmt.setInt(3, exam.getDuration());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exam.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static void addQuestion(Questions question) throws SQLException {
        String query = "INSERT INTO questions (exam_id, question_text, mark, correct_answer) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, question.getExamId());
            stmt.setString(2, question.getQuestionText());
            stmt.setInt(3, question.getMarks());
            stmt.setString(4, question.getCorrectAnswer());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    question.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static void startStudentExam(int studentId, int examId, Timestamp startTime) throws SQLException {
        String query = "INSERT INTO student_exams (student_id, exam_id, start_time, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, examId);
            stmt.setTimestamp(3, startTime);
            stmt.setString(4, "ongoing");
            stmt.executeUpdate();
        }
    }

    public static void submitStudentExam(int studentId, int examId, Timestamp endTime) throws SQLException {
        String query = "UPDATE student_exams SET end_time = ?, status = ? WHERE student_id = ? AND exam_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, endTime);
            stmt.setString(2, "completed");
            stmt.setInt(3, studentId);
            stmt.setInt(4, examId);
            stmt.executeUpdate();
        }
    }

    public static void addStudentAnswer(StudentAnswer answer) throws SQLException {
        String query = "INSERT INTO student_answers (student_id, question_id, answer, mark) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, answer.getStudentId());
            stmt.setInt(2, answer.getQuestionId());
            stmt.setString(3, answer.getAnswer());
            stmt.setInt(4, answer.getMark());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    answer.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static void addExamResult(ExamResult result) throws SQLException {
        String query = "INSERT INTO exam_results (student_id, exam_id, total_marks, feedback) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, result.getStudentID());
            stmt.setInt(2, result.getExamID());
            stmt.setInt(3, result.getTotalMarks());
            stmt.setString(4, result.getFeedback());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result.setExamResultID(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static int getLastInsertedExamId() throws SQLException {
        String query = "SELECT LAST_INSERT_ID()";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public static void addQuestionToExam(int examId, Questions question) throws SQLException {
        String query = "INSERT INTO questions (exam_id, question_text, marks, correct_answer) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, examId);
            stmt.setString(2, question.getQuestionText());
            stmt.setInt(3, question.getMarks());
            stmt.setString(4, question.getCorrectAnswer());
            stmt.executeUpdate();
        }
    }

    public static List<Questions> getQuestionsByExamId(int examId) throws SQLException {
        String query = "SELECT * FROM questions WHERE exam_id = ?";
        List<Questions> questions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, examId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Questions question = new Questions(
                            rs.getInt("exam_id"),
                            rs.getString("question_text"),
                            rs.getString("correct_answer"),
                            rs.getInt("marks")
                    );
                    question.setId(rs.getInt("id"));
                    questions.add(question);
                }
            }
        }
        return questions;
    }

    public static int getExamDurationById(int examId) throws SQLException {
        String query = "SELECT duration FROM exams WHERE exam_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, examId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("duration") * 60; // Convert to seconds
                }
            }
        }
        return 0;
    }

    public static void saveStudentExam(StudentExam studentExam) throws SQLException {
        String query = "INSERT INTO student_exams (student_id, exam_id, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, studentExam.getStudentId());
            stmt.setInt(2, studentExam.getExamId());
            stmt.setInt(3, studentExam.getScore());
            stmt.executeUpdate();
        }
    }
}
