package Commons;

//import com.sun.org.apache.regexp.internal.RE;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SummaryPageDAO {
    private DataSource pool;
    private String databaseName;

    public SummaryPageDAO(DataSource pool, String databaseName) {
        this.pool = pool;
        this.databaseName = databaseName;
    }

    public ArrayList<QuizAttempt> getUsersQuizAttempts(int user_id){
        Connection con = null;
        ArrayList<QuizAttempt> quizAttempts = new ArrayList<>();
        String query = "SELECT * FROM quiz_attempts WHERE user_id = ?";
        try{
            con = pool.getConnection();
            Statement statement = con.createStatement();
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, user_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int attempt_id = rs.getInt("AttemptID");
                int quiz_id = rs.getInt("QuizID");
                Time time_taken = rs.getTime("TimeTaken");
                BigDecimal decimal = rs.getBigDecimal("PercentCorrect");
                QuizAttempt attempt = new QuizAttempt(attempt_id, quiz_id, user_id, null, time_taken.getTime(), decimal);
                quizAttempts.add(attempt);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignored) {
            }
        }
        return quizAttempts;
    }
    public String getQuizDescription(int quizId) throws SQLException {
        Connection con = null;
        String description = null;
        try {
            con = pool.getConnection();

            Statement statement = con.createStatement();
            String query = "SELECT quiz_description FROM quizzes WHERE quiz_id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, quizId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                description= rs.getString("quiz_description");
            }
            preparedStatement.close();
            statement.close();

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignored) {
            }
        }
        return description;
    }


    public String getCreatorInfo(String quizId) throws SQLException {
        Connection con = null;
        String creatorId = null;

            try {
                con = pool.getConnection();

                Statement statement = con.createStatement();
            String query = "SELECT user_id FROM users WHERE user_id = (SELECT creator FROM quizzes WHERE quiz_id = ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1,quizId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                creatorId =  rs.getString("user_id");
            }

            pstmt.close();
            statement.close();

            } catch (SQLException e) {
                e.getStackTrace();
            } finally {
                if (con != null) try {
                    con.close();
                } catch (Exception ignored) {
                }
            }
        return creatorId;
    }

    public String getUsername(int userId){
        Connection connection = null;
        String result = "";
        try {
            connection = pool.getConnection();

            Statement statement = connection.createStatement();
            String query = "SELECT username FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    result = resultSet.getString("username");
                } else {
                    throw new IllegalArgumentException("No entry found with user_id: " + userId);
                }

                // Close statement and result set.
                resultSet.close();
                preparedStatement.close();
                statement.close();

            } catch (SQLException e) {
                e.getStackTrace();
            } finally {
                if (connection != null) try {
                    // Returns the connection to the pool.
                    connection.close();
                } catch (Exception ignored) {
                }
            }
        return result;
    }

    public List<QuizAttempt> getUserAttempts(int userId, int quizId, String orderBy) throws SQLException {
        Connection connection = null;
        List<QuizAttempt> attempts = new ArrayList<>();
        try {
            connection = pool.getConnection();

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM QuizAttempts WHERE UserID = ? AND QuizID = ? ORDER BY " + orderBy;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, quizId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                attempts.add(new QuizAttempt(rs.getInt("AttemptID"), rs.getInt("QuizID"), rs.getInt("UserID"),
                        rs.getTimestamp("AttemptDate"), rs.getTime("TimeTaken").getTime(), rs.getBigDecimal("PercentCorrect")));
            }
            rs.close();
            pstmt.close();
            statement.close();

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (connection != null) try {
                // Returns the connection to the pool.
                connection.close();
            } catch (Exception ignored) {
            }
        }
        return attempts;
    }

    public void saveQuizAttempt(QuizAttempt attempt) throws SQLException {
        Connection connection = null;
        try {
            connection = pool.getConnection();

            Statement statement = connection.createStatement();

            if (!attempt.isValid()) {
                throw new IllegalArgumentException("Invalid quiz attempt data");
            }

            String sql = "INSERT INTO QuizAttempts (QuizID, UserID, AttemptDate, TimeTaken, PercentCorrect) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, attempt.getQuizId());
            pstmt.setInt(2, attempt.getUserId());
            pstmt.setTimestamp(3, attempt.getAttemptDate());
            pstmt.setTime(4,new Time(attempt.getTimeTaken()));
            pstmt.setBigDecimal(5, attempt.getPercentCorrect());
            pstmt.executeUpdate();
            pstmt.close();
            statement.close();

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (connection != null) try {
                // Returns the connection to the pool.
                connection.close();
            } catch (Exception ignored) {
            }
        }
    }


    public List<QuizAttempt> getRecentTestTakersPerformance(int quizId) throws SQLException {
        Connection connection = null;
        List<QuizAttempt> attempts = new ArrayList<>();
        try {
            connection = pool.getConnection();

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM QuizAttempts WHERE QuizID = ? ORDER BY AttemptDate DESC LIMIT 10";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, quizId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                attempts.add(new QuizAttempt(rs.getInt("AttemptID"), rs.getInt("QuizID"), rs.getInt("UserID"),
                        rs.getTimestamp("AttemptDate"), rs.getTime("TimeTaken").getTime(), rs.getBigDecimal("PercentCorrect")));
            }
            rs.close();
            pstmt.close();
            statement.close();

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (connection != null) try {
                // Returns the connection to the pool.
                connection.close();
            } catch (Exception ignored) {
            }
        }
        return attempts;
    }

    public List<QuizAttempt> getHighestPerformersAllTime(int quizId) throws SQLException {
        Connection connection = null;
        List<QuizAttempt> attempts = new ArrayList<>();
        try {
            connection = pool.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT UserID, MAX(PercentCorrect) AS BestScore FROM QuizAttempts WHERE QuizID = ? GROUP BY UserID ORDER BY BestScore DESC LIMIT 10";

           PreparedStatement pstmt = connection.prepareStatement(sql);
           pstmt.setInt(1, quizId);
           ResultSet rs = pstmt.executeQuery();
           while (rs.next()) {
               QuizAttempt attempt = new QuizAttempt(
                       -1, // Since we're not using AttemptID here
                       quizId,
                       rs.getInt("UserID"),
                       null, // AttemptDate is not relevant here
                       0, // TimeTaken is not relevant here
                       rs.getBigDecimal("BestScore")
               );
               attempts.add(attempt);
           }
            rs.close();
            pstmt.close();
            statement.close();

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (connection != null) try {
                // Returns the connection to the pool.
                connection.close();
            } catch (Exception ignored) {
            }
        }
        return attempts;
    }


    public List<QuizAttempt> getTopPerformersDaily(int quizId) throws SQLException {
        Connection connection = null;
        List<QuizAttempt> attempts = new ArrayList<>();
        try {
            connection = pool.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT UserID, MAX(PercentCorrect) AS BestScore FROM QuizAttempts WHERE QuizID = ? AND AttemptDate > NOW() - INTERVAL '1 day' GROUP BY UserID ORDER BY BestScore DESC LIMIT 10";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, quizId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                attempts.add(new QuizAttempt(-1,
                        quizId,
                        rs.getInt("UserID"),
                        null,
0,
                        rs.getBigDecimal("BestScore")));
            }
            rs.close();
            pstmt.close();
            statement.close();

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (Exception ignored) {
            }
        }
        return attempts;
    }

    public SummaryStatistics getSummaryStatistics(int quizId) throws SQLException {
        Connection connection = null;
        SummaryStatistics summary = null;

        try {
            connection = pool.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT COUNT(*) as TotalAttempts, AVG(PercentCorrect) AS AverageScore, "+
                    "MAX(PercentCorrect) as HighestScore, MIN(PercentCorrect) as LowestScore, "+
                    "AVG(TIME_TO_SEC(TimeTaken)) AS AverageTimeSpent"+
                    "FROM QuizAttempts where QuizID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int totalAttempts = rs.getInt("TotalAttempts");
                BigDecimal averageScore = rs.getBigDecimal("AverageScore");
                BigDecimal highestScore = rs.getBigDecimal("HighestScore");
                BigDecimal lowestScore = rs.getBigDecimal("LowestScore");
                Time avgTime = rs.getTime("AverageTimeSpent");
                summary = new SummaryStatistics(quizId,averageScore,highestScore,lowestScore,totalAttempts,avgTime);


            }
            rs.close();
            pstmt.close();
            statement.close();

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (Exception ignored) {
            }
        }
        return summary;
    }

}
