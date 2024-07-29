import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The Database class provides methods to interact with the database
 * for managing users, exams, questions, student exams, student answers, and exam results.
 */
public class Database
{
    private static final String URL = "jdbc:mysql://localhost:3306/online_exam_system";
    private static final String USER = "root";
    private static final String PASSWORD = "K@yl13n!07LGD";

    /**
     * Adds a new user to the database.
     *
     * @param user The user to be added.
     * @throws SQLException if a database access error occurs.
     */
    public static void addUser(User user) throws SQLException
    {
        String query = "INSERT INTO users (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement prepSt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepSt.setString(1, user.getFirstName());
            prepSt.setString(2, user.getLastName());
            prepSt.setString(3, user.getEmail());
            prepSt.setString(4, user.getPassword());
            prepSt.setString(5, user.getRole());
            prepSt.executeUpdate();

            ResultSet rs = prepSt.getGeneratedKeys();
            if (rs.next())
            {
                user.setId(rs.getInt(1));
            }
        }
    }

    /**
     * Validates user login credentials.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The User object if the credentials are valid, otherwise null.
     * @throws SQLException if a database access error occurs.
     */
    public static User validateLogin(String email, String password) throws SQLException
    {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement prepStm = conn.prepareStatement(query))
        {
            prepStm.setString(1, email);
            prepStm.setString(2, password);
            ResultSet rs = prepStm.executeQuery();
            if (rs.next())
            {
                return new User(rs.getInt("user_id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("role"));
            } else
            {
                return null;
            }
        }
    }

    /**
     * Adds a new exam to the database.
     *
     * @param teacherId The ID of the teacher creating the exam.
     * @param title The title of the exam.
     * @param duration The duration of the exam in minutes.
     */
    public static void addExam(int teacherId, String title, int duration)
    {
        String query = "INSERT INTO exams (teacher_id, title, duration) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, teacherId);
            statement.setString(2, title);
            statement.setInt(3, duration);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
            {
                generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a student exam by recording the start time and status in the database.
     *
     * @param studentId The ID of the student taking the exam.
     * @param examId The ID of the exam.
     * @param startTime The start time of the exam.
     * @throws SQLException if a database access error occurs.
     */
    public static void startStudentExam(int studentId, int examId, Timestamp startTime) throws SQLException
    {
        String query = "INSERT INTO student_exams (student_id, exam_id, start_time, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setInt(1, studentId);
            stmt.setInt(2, examId);
            stmt.setTimestamp(3, startTime);
            stmt.setString(4, "ongoing");
            stmt.executeUpdate();
        }
    }

    /**
     * Submits a student exam by recording the end time and status in the database.
     *
     * @param studentId The ID of the student taking the exam.
     * @param examId The ID of the exam.
     * @param endTime The end time of the exam.
     * @throws SQLException if a database access error occurs.
     */
    public static void submitStudentExam(int studentId, int examId, Timestamp endTime) throws SQLException
    {
        String query = "UPDATE student_exams SET end_time = ?, status = ? WHERE student_id = ? AND exam_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setTimestamp(1, endTime);
            stmt.setString(2, "completed");
            stmt.setInt(3, studentId);
            stmt.setInt(4, examId);
            stmt.executeUpdate();
        }
    }

    /**
     * Adds a student's answer to a question in the database.
     *
     * @param answer The student's answer.
     * @throws SQLException if a database access error occurs.
     */
    public static void addStudentAnswer(StudentAnswer answer) throws SQLException
    {
        String query = "INSERT INTO student_answers (student_id, question_id, answer, mark) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setInt(1, answer.getStudentId());
            stmt.setInt(2, answer.getQuestionId());
            stmt.setString(3, answer.getAnswer());
            stmt.setInt(4, answer.getMark());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next())
                {
                    answer.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    /**
     * Retrieves the last inserted exam ID from the database.
     *
     * @return The last inserted exam ID.
     * @throws SQLException if a database access error occurs.
     */
    public static int getLastInsertedExamId() throws SQLException
    {
        String query = "SELECT MAX(exam_id) AS last_id FROM exams";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                return rs.getInt("last_id");
            } else
            {
                throw new SQLException("Failed to retrieve last inserted exam ID.");
            }
        }
    }

    /**
     * Adds a question to an existing exam in the database.
     *
     * @param examId The ID of the exam.
     * @param question The question to be added.
     * @throws SQLException if a database access error occurs.
     */
    public static void addQuestionToExam(int examId, Questions question) throws SQLException
    {
        String query = "INSERT INTO questions (exam_id, question_text, mark, correct_answer) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setInt(1, examId);
            stmt.setString(2, question.getQuestionText());
            stmt.setInt(3, question.getMarks());
            stmt.setString(4, question.getCorrectAnswer());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    question.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    /**
     * Retrieves all exams from the database.
     *
     * @return A list of exams.
     * @throws SQLException if a database access error occurs.
     */
    public static List<Exam> getAllExams() throws SQLException
    {
        List<Exam> exams = new ArrayList<>();
        String query = "SELECT * FROM exams";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                Exam exam = new Exam(rs.getInt("exam_id"), rs.getInt("teacher_id"),
                        rs.getString("title"), rs.getInt("duration"));
                exams.add(exam);
            }
        }
        return exams;
    }

    /**
     * Retrieves questions for a specific exam from the database.
     *
     * @param examId The ID of the exam.
     * @return A list of questions for the exam.
     * @throws SQLException if a database access error occurs.
     */
    public static List<Questions> getQuestionsByExamId(int examId) throws SQLException {
        List<Questions> questionsList = new ArrayList<>();
        String query = "SELECT question_id, question_text, correct_answer, mark FROM questions WHERE exam_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, examId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int questionId = rs.getInt("question_id");
                String questionText = rs.getString("question_text");
                String correctAnswer = rs.getString("correct_answer");
                int marks = rs.getInt("mark");

                Questions question = new Questions(questionId, examId, questionText, marks, correctAnswer);
                questionsList.add(question);
            }
        }

        return questionsList;
    }

    /**
     * Retrieves all exams for a specific teacher from the database.
     *
     * @param teacherId The ID of the teacher.
     * @return A list of exams created by the teacher.
     * @throws SQLException if a database access error occurs.
     */
    public static List<Exam> getExamsByTeacherId(int teacherId) throws SQLException
    {
        List<Exam> exams = new ArrayList<>();
        String query = "SELECT * FROM exams WHERE teacher_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                Exam exam = new Exam(rs.getInt("exam_id"), rs.getInt("teacher_id"),
                        rs.getString("title"), rs.getInt("duration"));
                exams.add(exam);
            }
        }
        return exams;
    }

    /**
     * Saves the exam results to the database.
     *
     * @param examResult The exam result to be saved.
     * @throws SQLException if a database access error occurs.
     */
    public static void saveExamResults(ExamResult examResult) throws SQLException {
        String query = "INSERT INTO exam_results (student_id, exam_id, total_marks, feedback) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, examResult.getStudentId());
            stmt.setInt(2, examResult.getExamId());
            stmt.setInt(3, examResult.getTotalMarks());
            stmt.setString(4, examResult.getFeedback());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    examResult.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    /**
     * Saves a student's answer to the database.
     *
     * @param studentAnswer The student's answer to be saved.
     * @throws SQLException if a database access error occurs.
     */
    public static void saveStudentAnswer(StudentAnswer studentAnswer) throws SQLException
    {
        if (!isQuestionIdValid(studentAnswer.getQuestionId()))
        {
            throw new SQLException("Invalid question_id: " + studentAnswer.getQuestionId());
        }
        addStudentAnswer(studentAnswer);
    }

    /**
     * Retrieves an exam by its ID from the database.
     *
     * @param examId The ID of the exam to be retrieved.
     * @return The Exam object if found, otherwise null.
     * @throws SQLException if a database access error occurs.
     */
    public static Exam getExamById(int examId) throws SQLException {
        String query = "SELECT * FROM exams WHERE exam_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, examId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Exam(rs.getInt("exam_id"), rs.getInt("teacher_id"),
                        rs.getString("title"), rs.getInt("duration"));
            } else {
                return null;
            }
        }
    }

    /**
     * Retrieves all exam IDs from the database.
     *
     * @return A list of all exam IDs.
     * @throws SQLException if a database access error occurs.
     */
    public static List<Integer> getAllExamIds() throws SQLException {
        String query = "SELECT exam_id FROM exams";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            List<Integer> examIds = new ArrayList<>();
            while (rs.next()) {
                examIds.add(rs.getInt("exam_id"));
            }
            return examIds;
        }
    }

    public static boolean isQuestionIdValid(int questionId) throws SQLException {
        String query = "SELECT COUNT(*) FROM questions WHERE question_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement prepS = conn.prepareStatement(query)) {
            prepS.setInt(1, questionId);
            ResultSet rs = prepS.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}
