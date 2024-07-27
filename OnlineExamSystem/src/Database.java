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
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getRole());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        }
    }

    public static User validateLogin(String email, String password) throws SQLException
    {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement prepStm = conn.prepareStatement(query)) {
            prepStm.setString(1, email);
            prepStm.setString(2, password);
            ResultSet rs = prepStm.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("first_name"),
                        rs.getString("last_name"),rs.getString("email"),
                        rs.getString("password"), rs.getString("role"));
            } else {
                return null;
            }
        }
    }

    public static void addExam(int teacherId, String title, int duration) {
        String query = "INSERT INTO exams (teacher_id, title, duration) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, teacherId);
            statement.setString(2, title);
            statement.setInt(3, duration);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            stmt.setInt(1, result.getStudentId());
            stmt.setInt(2, result.getExamId());
            stmt.setInt(3, result.getTotalMarks());
            stmt.setString(4, result.getFeedback());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result.setId(generatedKeys.getInt(1));
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

    public static void addQuestionToExam(int examId, Questions question) throws SQLException
    {
        if (!doesExamIdExist(examId)) {
            System.err.println("Exam ID " + examId + " does not exist. Cannot add question.");
            return;
        }

        String query = "INSERT INTO questions (exam_id, question_text, mark, correct_answer) VALUES (?, ?, ?, ?)";
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
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, examId);
            ResultSet rs = pstmt.executeQuery();
            List<Questions> questions = new ArrayList<>();
            while (rs.next()) {
                Questions question = new Questions(rs.getInt("exam_id"), rs.getString("question_text"),
                        rs.getString("correct_answer"), rs.getInt("mark"));
                question.setId(rs.getInt("question_id"));
                questions.add(question);
            }
            return questions;
        }
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
        String query = "INSERT INTO student_exams (student_id, exam_id, start_time, end_time, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, studentExam.getStudentId());
            pstmt.setInt(2, studentExam.getExamId());
            pstmt.setTimestamp(3, new Timestamp(studentExam.getStartTime().getTime()));
            pstmt.setTimestamp(4, studentExam.getEndTime() != null ?
                                new Timestamp(studentExam.getEndTime().getTime()) : null);
            pstmt.setString(5, studentExam.getStatus());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                studentExam.setId(rs.getInt(1));
            }
        }
    }

    public static void saveExamResults(ExamResult examResults) throws SQLException {
        String sql = "INSERT INTO exam_results (student_id, exam_id, feedback) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement prepStatement = conn.prepareStatement(sql)) {
            prepStatement.setInt(1, examResults.getStudentId());
            prepStatement.setInt(2, examResults.getExamId());
            prepStatement.setString(3, examResults.getFeedback());
            prepStatement.executeUpdate();
        }
    }

    public static Exam getExamById(int examId) throws SQLException {
        String query = "SELECT * FROM exams WHERE exam_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, examId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Exam(rs.getInt("exam_id"), rs.getInt("teacher_id"),
                        rs.getString("title"), rs.getInt("duration"));
            } else {
                throw new SQLException("Exam not found");
            }
        }
    }

    public static void saveStudentAnswer(StudentAnswer studentAnswer) throws SQLException {
        String query = "INSERT INTO student_answers (student_id, question_id, answer, mark) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, studentAnswer.getStudentId());
            pstmt.setInt(2, studentAnswer.getQuestionId());
            pstmt.setString(3, studentAnswer.getAnswer());
            pstmt.setInt(4, studentAnswer.getMark());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                studentAnswer.setId(rs.getInt(1));
            }
        }
    }

    public static List<Integer> getAllExamIds() throws SQLException {
        String query = "SELECT exam_id FROM exams";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            List<Integer> examIds = new ArrayList<>();
            while (rs.next()) {
                examIds.add(rs.getInt("exam_id"));
            }
            return examIds;
        }
    }

    public static boolean doesExamIdExist(int examId) {
        String query = "SELECT COUNT(*) FROM exams WHERE exam_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, examId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
