import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/examsystem";
        String username = "root";
        String password = "K@yl13n!07LGD";
        return DriverManager.getConnection(url, username, password);
    }

    // Add user (either student or teacher)
    public static void addUser(User user, String role) throws SQLException {
        String userQuery = "INSERT INTO users (first_name, last_name, email, role, password, username) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS)) {
            userStmt.setString(1, user.getFirstName());
            userStmt.setString(2, user.getLastName());
            userStmt.setString(3, user.getEmail());
            userStmt.setString(4, role);
            userStmt.setString(5, user.getPassword());
            userStmt.setString(6, user.getUsername());
            userStmt.executeUpdate();
            try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        }

        if (role.equals("student") && user instanceof Student student) {
            String studentQuery = "INSERT INTO students (user_id, year_of_study, major) VALUES (?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement studentStmt = conn.prepareStatement(studentQuery)) {
                studentStmt.setInt(1, user.getId());
                studentStmt.setInt(2, student.getYearOfStudy());
                studentStmt.setString(3, student.getMajor());
                studentStmt.executeUpdate();
            }
        } else if (role.equals("teacher") && user instanceof Teacher teacher) {
            String teacherQuery = "INSERT INTO teachers (user_id, department, years_of_experience) VALUES (?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement teacherStmt = conn.prepareStatement(teacherQuery)) {
                teacherStmt.setInt(1, user.getId());
                teacherStmt.setString(2, teacher.getDepartment());
                teacherStmt.setInt(3, teacher.getYearsOfExperience());
                teacherStmt.executeUpdate();
            }
        }
    }

    public static User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("user_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email = rs.getString("email");
                    String role = rs.getString("role");
                    String password = rs.getString("password");
                    if (role.equals("student")) {
                        return getStudentById(id, firstName, lastName, email, password);
                    } else if (role.equals("teacher")) {
                        return getTeacherById(id, firstName, lastName, email, password);
                    }
                }
            }
        }
        return null;
    }

    private static Student getStudentById(int id, String firstName, String lastName, String email, String password) throws SQLException {
        String query = "SELECT * FROM students WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int yearOfStudy = rs.getInt("year_of_study");
                    String major = rs.getString("major");
                    return new Student(id, firstName, lastName, email, password, yearOfStudy, major);
                }
            }
        }
        return null;
    }

    private static Teacher getTeacherById(int id, String firstName, String lastName, String email, String password) throws SQLException {
        String query = "SELECT * FROM teachers WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String department = rs.getString("department");
                    int yearsOfExperience = rs.getInt("years_of_experience");
                    return new Teacher(id, firstName, lastName, email, password, department, yearsOfExperience);
                }
            }
        }
        return null;
    }

    public static boolean authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static void saveExam(Exam exam) throws SQLException {
        String query = "INSERT INTO exams (teacher_id, title, duration) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
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

        for (Questions question : exam.getQuestions()) {
            addQuestion(question, exam.getId());
        }
    }

    public static void addQuestion(Questions question, int examId) throws SQLException {
        String query = "INSERT INTO exam_questions (exam_id, question_text, correct_answer, type) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, examId);
            stmt.setString(2, question.getQuestionText());
            stmt.setString(3, question.getCorrectAnswer());
            stmt.setString(4, question.getType());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    question.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static List<Exam> getAvailableExams() throws SQLException {
        String query = "SELECT * FROM exams";
        List<Exam> exams = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("exam_id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                int teacherId = rs.getInt("teacher_id");
                List<Questions> questions = getQuestionsByExamId(id);
                exams.add(new Exam(id, title, duration, teacherId, questions));
            }
        }
        return exams;
    }

    public static List<Questions> getQuestionsByExamId(int examId) throws SQLException {
        String query = "SELECT * FROM exam_questions WHERE exam_id = ?";
        List<Questions> questions = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, examId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("exam_questions_id");
                    String questionText = rs.getString("question_text");
                    String correctAnswer = rs.getString("correct_answer");
                    String type = rs.getString("type");
                    questions.add(new Questions(id, questionText, correctAnswer, type));
                }
            }
        }
        return questions;
    }

    public static int submitExam(int studentId, int examId, List<String> studentAnswers) throws SQLException {
        List<Questions> questions = getQuestionsByExamId(examId);
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCorrectAnswer().equals(studentAnswers.get(i))) {
                score++;
            }
        }

        String feedback = "Your score is " + score + " out of " + questions.size();
        addExamResult(new ExamResult(0, examId, studentId, score, feedback));

        return score;
    }

    public static void addExamResult(ExamResult examResult) throws SQLException {
        String query = "INSERT INTO exam_results (exam_id, student_id, score, feedback) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, examResult.getExamId());
            stmt.setInt(2, examResult.getStudentId());
            stmt.setInt(3, examResult.getScore());
            stmt.setString(4, examResult.getFeedback());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    examResult.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static List<ExamResult> getExamResultsByStudentId(int studentId) throws SQLException {
        String query = "SELECT * FROM exam_results WHERE student_id = ?";
        List<ExamResult> examResults = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int examId = rs.getInt("exam_id");
                    int score = rs.getInt("score");
                    String feedback = rs.getString("feedback");
                    examResults.add(new ExamResult(id, examId, studentId, score, feedback));
                }
            }
        }
        return examResults;
    }

    public static Exam getExamById(int id) throws SQLException {
        String query = "SELECT * FROM exams WHERE exam_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("title");
                    int duration = rs.getInt("duration");
                    int teacherId = rs.getInt("teacher_id");
                    List<Questions> questions = getQuestionsByExamId(id);
                    return new Exam(id, title, duration, teacherId, questions);
                }
            }
        }
        return null;
    }

    public static String getFeedback(int studentId, int examId) throws SQLException {
        String query = "SELECT feedback FROM exam_results WHERE student_id = ? AND exam_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, examId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("feedback");
                }
            }
        }
        return "No feedback available";
    }


}
