package Commons;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnection {
    private Connection con;
    private Statement stmt;
    private ResultSet resultSet;
    private String baseURL = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String password = "barbariko15";

    public DBConnection(String database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(baseURL, user, password);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.execute(String.format("USE %s", database));
        } catch (Exception e) {
            System.out.println("ERROR: Could not connect to database " + database);
            e.printStackTrace();
        }
    }

    /**
     * @param username      - username of user
     * @param password_hash - hash value of password, calculated by SHA-1 algorithm (3rd assignment algo)
     * @param salt
     * @return boolean value - whether entry was inserted into the database
     */
    public boolean addUser(String username, String password_hash, String salt){
        int result = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO users");
        builder.append(" (username, password_hashed, salt) ");
        builder.append(" VALUES ");
        builder.append(String.format("(\'%s\', \'%s\', \'%s\')", username, password_hash, salt));
        try {
            String query = builder.toString();
            result = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (result==0){
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Could not add user \'" + username + "\' to the database");
        } catch (SQLException e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result == 1;
    }

    /**
     *
     * @param username - username of the user
     * @return hash value of password
     */
    public String getPasswordHash(String username) {
        String query = String.format("SELECT password_hashed FROM users WHERE username = \'%s\'", username);
        String result = "";
        try {
            this.resultSet = stmt.executeQuery(query);

            if (this.resultSet.next()) {
                // means that there was entry with provided username
                result = resultSet.getString(1);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Could not find entry with username \'" + username + "\'");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param username    - username of the user
     * @param newPassword - new hashed password
     * @param salt
     * @return boolean - whether entry was updated
     */
    public boolean changePassword(String username, String newPassword, String salt) {
        String query = String.format("UPDATE users SET password_hashed = \'%s\', salt = \'%s\' WHERE username = \'%s\'", newPassword, salt, username);
        try {
            int result = stmt.executeUpdate(query);
            if (result == 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Could not change password of user \'" + username + "\' to the database");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
            return false;
        }
        return true;
    }



    /**
     * @return - number of rows
     */
    public int getCount() {
        int result = 0;
        try {
            this.resultSet = stmt.executeQuery("SELECT * FROM users");
            this.resultSet.last();
            result = this.resultSet.getRow();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param sender
     * @param receiver
     */
    public boolean sendFriendRequest(String sender, String receiver) {
        String query = String.format("INSERT INTO friends VALUES (\'%s\', \'%s\', \'%s\')", getID(sender), getID(receiver), "pending");
        int result = 0;
        boolean senderExists = accountExists(sender);
        boolean receiverExists = accountExists(receiver);
        if (senderExists && receiverExists) {
            try {
                result = stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("One of the provided accounts does not exists");
        }
        return result == 1;
    }

    public boolean acceptFriendRequest(String accepter, String requester) {
        boolean requestExists = false;
        String query = String.format("SELECT * FROM friends WHERE user_id = \'%s\' AND friend_id = \'%s\' AND friendship_status = \'pending\'", getID(requester), getID(accepter));
        try {
            resultSet = stmt.executeQuery(query);
            requestExists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!requestExists) {
            return false;
        }

        String updateQuery = String.format("UPDATE friends SET friendship_status = \'friends\' WHERE user_id = \'%s\' and friend_id = \'%s\'", getID(requester), getID(accepter), "friends");
        try {
            int rowsAffected = stmt.executeUpdate(updateQuery);
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateQuery = String.format("INSERT INTO friends VALUES (\'%s\', \'%s\', \'%s\')", getID(accepter), getID(requester), "friends");
        try {
            int rowsAffected = stmt.executeUpdate(updateQuery);
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private boolean accountExists(String username) {
        String query = String.format("SELECT * FROM users WHERE username = \'%s\'", username);
        try {
            resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private String getID(String username) {
        String query = String.format("SELECT user_id FROM users WHERE username = \'%s\'", username);
        String result = "";
        try {
            resultSet = stmt.executeQuery(query);
            resultSet.next();
            result = String.valueOf(resultSet.getInt("user_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
    }

    /**
     * @param username username of
     * @return
     */
    public String getSalt(String username) {
        String query = String.format("SELECT salt FROM users WHERE username = \'%s\'", username);
        String result = "";
        try {
            this.resultSet = stmt.executeQuery(query);

            if (this.resultSet.next()) {
                // means that there was entry with provided username
                result = resultSet.getString(1);
            }else {
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }catch(SQLException e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
       return result;
    }

    public int getUserId(String username){
        String query = String.format("SELECT user_id FROM users WHERE username  = \'%s\' " , username);
        int result = -1;
        try {
            this.resultSet = stmt.executeQuery(query);

            if(this.resultSet.next()){
                // means that there was entry with provided username
                result = resultSet.getInt(1);
            }else {
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e) {
            System.out.println("ERROR: Could not find entry with username \'" + username + "\'");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public String getUsername(int userId){
        String query = String.format("SELECT username FROM users WHERE user_id  = \'%d\' " , userId);
        String result = "";
        try {
            this.resultSet = stmt.executeQuery(query);

            if(this.resultSet.next()){
                // means that there was entry with provided userid
                result = resultSet.getString(1);
            }else {
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e) {
            System.out.println("ERROR: Could not find entry with user_id \'" + userId + "\'");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getFriendsNames(String username){
        int id = getUserId(username);
        String query = String.format("SELECT friend_id , status FROM users WHERE user_id  = \'%d\' " , id);
        ArrayList<String> result = new ArrayList<String>();
        try {
            this.resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                if(resultSet.getString(2).equals("active"))
                    result.add(getUsername(resultSet.getInt(1)));
            }
        }catch(Exception e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getFriendRequestUsernames(String username){
        int id = getUserId(username);
        String query = String.format("SELECT user_id , status FROM users WHERE friend_id  = \'%d\' " , id);
        ArrayList<String> result = new ArrayList<String>();
        try {
            this.resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                if(resultSet.getString(2) .equals("pending"))
                    result.add(getUsername(resultSet.getInt(1)));
            }
        }catch(Exception e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public String getQuizName(int id){
        String query = String.format("SELECT quiz_name FROM quizzes WHERE quiz_id  = \'%d\' " , id);
        String result = "";
        try {
            this.resultSet = stmt.executeQuery(query);
            if(this.resultSet.next()){
                // means that there was entry with provided quiz_id
                result = resultSet.getString(1);
            }else {
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e) {
            System.out.println("ERROR: Could not find entry with quiz_id \'" + id + "\'");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Integer> getUsersMadeQuizIds(String username){
        int id = getUserId(username);
        String query = String.format("SELECT quiz_id FROM quizzes WHERE user_id  = \'%d\' " , id);
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            this.resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                result.add(resultSet.getInt(1));
            }
        }catch(Exception e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Integer> getRecentlyAddedQuizzes(int max_count){
        String query = String.format("SELECT quiz_id  FROM quizzes ORDER BY creation_date ");
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            this.resultSet = stmt.executeQuery(query);
            while(resultSet.next() && max_count > 0){
                result.add(resultSet.getInt(1));
                max_count--;
            }
        }catch(Exception e) {
            System.out.println(" ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Integer> getPopularQuizzes(int maxCount){
        String query = String.format("SELECT quiz_id FROM quizzes ORDER BY write_count DESC");
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            this.resultSet = stmt.executeQuery(query);
            while(resultSet.next() && maxCount > 0){
                result.add(resultSet.getInt(1));
                maxCount--;
            }
        }catch(Exception e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }





    public boolean createQuiz(Quiz quiz) {
        String insertSQL = "INSERT INTO quizzes (quiz_name, quiz_description, author_id, random_questions_option, page_options, immediate_correction) VALUES (?,?,?,?,?,?)";
        int quiz_id = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);


            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setString(2, quiz.getQuizDescription());
            preparedStatement.setString(3, getID(quiz.creator().getUsername()));
            preparedStatement.setBoolean(4, quiz.getQuizOptions().contains(Quiz.QuizOptions.RANDOM_QUESTIONS) ? true : false);
            preparedStatement.setString(5, quiz.getQuizOptions().contains(Quiz.QuizOptions.MULTIPLE_PAGES) ? "multiple-page" : "one-page");
            preparedStatement.setBoolean(6, quiz.getQuizOptions().contains(Quiz.QuizOptions.IMMEDIATE_CORRECTION) ? true : false);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        quiz_id = rs.getInt(1);
                    }
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // if reached here, entry in quizzes is inserted, but questions are not populated
        for (Question question : quiz.getQuestions()) {
            insertSQL = "INSERT INTO questions (question_text, question_num, quiz_id, image_url, multiple_choice) VALUES (?, ?, ?, ?, ?)";
            int question_id = 0;
            try {
                PreparedStatement preparedStatement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, question.getQuestion());
                preparedStatement.setInt(2, question.getQuestionIndex());
                preparedStatement.setInt(3, quiz_id);
                if (question.getUrl() == null || question.getUrl().isEmpty()) {
                    preparedStatement.setNull(4, Types.VARCHAR);
                } else {
                    preparedStatement.setString(4, question.getUrl());
                }
                preparedStatement.setBoolean(5, question.getQuestionType() == Question.QuestionType.MULTIPLE_CHOICE);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                        if (rs.next()) {
                            question_id = rs.getInt(1);
                        }
                    }
                } else {
                    return false;
                }


            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            if (question.getQuestionType() == Question.QuestionType.MULTIPLE_CHOICE) {
                insertSQL = "INSERT INTO multiple_choice_answers (option_char, answer_text, question_id, correct_answer) VALUES (?, ?, ?, ?)";
                char option_char = 'A';
                for (Answer answer : question.getAnswers()) {
                    try {
                        PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
                        preparedStatement.setString(1, String.valueOf(option_char));
                        option_char++;
                        preparedStatement.setString(2, answer.getAnswer());
                        preparedStatement.setInt(3, question_id);
                        preparedStatement.setBoolean(4, answer.isCorrect());
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

            }
        }


        return true;
    }

    public String getUsername(int userId){
        String query = String.format("SELECT username FROM users WHERE user_id  = \'%d\' " , userId);
        String result = "";
        try {
            this.resultSet = stmt.executeQuery(query);

            if(this.resultSet.next()){
                // means that there was entry with provided userid
                result = resultSet.getString(1);
            }else {
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e) {
            System.out.println("ERROR: Could not find entry with user_id \'" + userId + "\'");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }


    public String getQuizDescription(int quizId) throws SQLException {
        String query = "SELECT quiz_description FROM quizzes WHERE quiz_id = ?";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, quizId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return rs.getString("quiz_description");
        }
        return null;

    }

    public String getCreatorInfo(String quizId) throws SQLException {
        String query = "SELECT user_id FROM users WHERE user_id = (SELECT author_id FROM quizzes WHERE quiz_id = ?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1,quizId);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            return rs.getString("user_id");
        }
        return null;
    }

    public List<QuizAttempt> getUserAttempts(int userId, int quizId, String orderBy) throws SQLException {
        String sql = "SELECT * FROM QuizAttempts WHERE UserID = ? AND QuizID = ? ORDER BY " + orderBy;
        List<QuizAttempt> attempts = new ArrayList<>();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, userId);
        pstmt.setInt(2, quizId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            attempts.add(new QuizAttempt(rs.getInt("AttemptID"), rs.getInt("QuizID"), rs.getInt("UserID"),
                    rs.getTimestamp("AttemptDate"), rs.getTime("TimeTaken"), rs.getBigDecimal("PercentCorrect")));
        }

        return attempts;
    }

    public void saveQuizAttempt(QuizAttempt attempt) throws SQLException {
        if (!attempt.isValid()) {
            throw new IllegalArgumentException("Invalid quiz attempt data");
        }

        String sql = "INSERT INTO QuizAttempts (QuizID, UserID, AttemptDate, TimeTaken, PercentCorrect) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, attempt.getQuizId());
        pstmt.setInt(2, attempt.getUserId());
        pstmt.setTimestamp(3, attempt.getAttemptDate());
        pstmt.setTime(4, attempt.getTimeTaken());
        pstmt.setBigDecimal(5, attempt.getPercentCorrect());
        pstmt.executeUpdate();
    }


    public List<QuizAttempt> getRecentTestTakersPerformance(int quizId) throws SQLException {
        String sql = "SELECT * FROM QuizAttempts WHERE QuizID = ? ORDER BY AttemptDate DESC LIMIT 10";
        List<QuizAttempt> attempts = new ArrayList<>();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, quizId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            attempts.add(new QuizAttempt(rs.getInt("AttemptID"), rs.getInt("QuizID"), rs.getInt("UserID"),
                    rs.getTimestamp("AttemptDate"), rs.getTime("TimeTaken"), rs.getBigDecimal("PercentCorrect")));
        }

        return attempts;
    }

    public List<QuizAttempt> getHighestPerformersAllTime(int quizId) throws SQLException {
        String sql = "SELECT UserID, MAX(PercentCorrect) AS BestScore FROM QuizAttempts WHERE QuizID = ? GROUP BY UserID ORDER BY BestScore DESC LIMIT 10";
        List<QuizAttempt> attempts = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, quizId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                QuizAttempt attempt = new QuizAttempt(
                        -1, // Since we're not using AttemptID here
                        quizId,
                        rs.getInt("UserID"),
                        null, // AttemptDate is not relevant here
                        null, // TimeTaken is not relevant here
                        rs.getBigDecimal("BestScore")
                );
                attempts.add(attempt);
            }
        }
        return attempts;
    }

    public List<QuizAttempt> getTopPerformersDaily(int quizId) throws SQLException {
        List<QuizAttempt> attempts = new ArrayList<>();
        String sql = "SELECT UserID, MAX(PercentCorrect) AS BestScore FROM QuizAttempts WHERE QuizID = ? AND AttemptDate > NOW() - INTERVAL '1 day' GROUP BY UserID ORDER BY BestScore DESC LIMIT 10";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,quizId);
        ResultSet rs= pstmt.executeQuery();
        while(rs.next()){
            attempts.add(new QuizAttempt(-1,
                    quizId,
                    rs.getInt("UserID"),
                    null,
                    null,
                    rs.getBigDecimal("BestScore")));
        }
        return attempts;

    }

    public SummaryStatistics getSummaryStatistics(int quizId) throws SQLException {
        String sql = "SELECT COUNT(*) as TotalAttempts, AVG(PercentCorrect) AS AverageScore, "+
                "MAX(PercentCorrect) as HighestScore, MIN(PercentCorrect) as LowestScore, "+
                "AVG(TIME_TO_SEC(TimeTaken)) AS AverageTimeSpent"+
                "FROM QuizAttempts where QuizID = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            int totalAttempts = rs.getInt("TotalAttempts");
            BigDecimal averageScore = rs.getBigDecimal("AverageScore");
            BigDecimal highestScore = rs.getBigDecimal("HighestScore");
            BigDecimal lowestScore = rs.getBigDecimal("LowestScore");
            Time avgTime = rs.getTime("AverageTimeSpent");
            SummaryStatistics summary = new SummaryStatistics(quizId,averageScore,highestScore,lowestScore,totalAttempts,avgTime);
            return summary;

        }
        return null;
    }




//    public List<UserPerformance> getHighestPerformersAllTime(int quizId) throws SQLException {
//        String sql = "SELECT UserID, MAX(PercentCorrect) AS BestScore FROM QuizAttempts WHERE QuizID = ? GROUP BY UserID ORDER BY BestScore DESC LIMIT 10";
//        List<UserPerformance> performances = new ArrayList<>();
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        pstmt.setInt(1, quizId);
//        ResultSet rs = pstmt.executeQuery();
//        while (rs.next()) {
//            performances.add(new UserPerformance(rs.getInt("UserID"), rs.getBigDecimal("BestScore")));
//        }
//        return performances;
//    }

//    public List<UserPerformance> getTopPerformersLastDay(int quizId) throws SQLException {
//        String sql = "SELECT UserID, MAX(PercentCorrect) AS BestScore FROM QuizAttempts WHERE QuizID = ? AND AttemptDate > NOW() - INTERVAL '1 day' GROUP BY UserID ORDER BY BestScore DESC LIMIT 10";
//        List<UserPerformance> performances = new ArrayList<>();
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        pstmt.setInt(1, quizId);
//        ResultSet rs = pstmt.executeQuery();
//        while (rs.next()) {
//            performances.add(new UserPerformance(rs.getInt("UserID"), rs.getBigDecimal("BestScore")));
//        }
//
//        return performances;
//    }


//    public void updateUserPerformance(QuizAttempt attempt) throws SQLException {
//        UserPerformance performance = getUserPerformance(attempt.getUserId(),attempt.getQuizId());
//    }
//
//    private UserPerformance getUserPerformance(int userId, int quizId) throws SQLException {
//        String sql = "SELECT * FROM UserPerformance where userId=? AND quizId=?";
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        pstmt.setInt(1,userId);
//        pstmt.setInt(2,quizId);
//        ResultSet rs  = pstmt.executeQuery();
//
//        while(rs.next()){
//
//            UserPerformance userPerformance = new UserPerformance
//                    (rs.getInt("UserID"),
//                            rs.getInt("quizID"),
//                            rs.getBigDecimal("HighestScore"),
//                            rs.getBigDecimal("AverageScore"),
//                            rs.getTime("TotalTimeSpent"),
//                            rs.getTime("BestTime"),
//                            )
//        }
//
//
//
//    }

//    public PerformanceStats getSummaryStatistics(int quizId) throws SQLException {
//        String sql = "SELECT * FROM PerformanceStats WHERE QuizID = ?";
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, quizId);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return new PerformanceStats(rs.getInt("StatID"), rs.getInt("QuizID"), rs.getBigDecimal("AverageScore"), rs.getInt("TotalAttempts"));
//            }
//
//        return null;
//    }
//
//    public boolean canEditQuiz(int quizId, int userId) throws SQLException {
//        String sql = "SELECT CreatorID FROM Quizzes WHERE QuizID = ?";
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        pstmt.setInt(1, quizId);
//        ResultSet rs = pstmt.executeQuery();
//        if (rs.next()) {
//            return rs.getInt("CreatorID") == userId;
//        }
//        return false;
//    }
}

