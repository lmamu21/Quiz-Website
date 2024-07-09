/**
 * Data Access Object (DAO) for managing achievements in the database.
 * Provides methods to create, retrieve, and manage achievements for users.
 */
package Commons.Achievement;

import Commons.Announcement.Announcement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AchievementDAO {

    // DataSource for managing database connections.
    private DataSource pool;
    // Name of the database being accessed.
    private String databaseName;

    /**
     * Constructor for AchievementDAO.
     * @param pool The DataSource to obtain database connections.
     * @param databaseName The name of the database to access.
     */
    public AchievementDAO(DataSource pool, String databaseName) {
        this.pool = pool;
        this.databaseName = databaseName;
    }

    /**
     * Creates a new achievement for a user and inserts it into the database.
     * @param userID The ID of the user earning the achievement.
     * @param award The type of achievement being earned.
     * @return The created Achievement object.
     * @throws SQLException If an SQL exception occurs.
     */
    public Achievement createAchievement(int userID, Achievement.Award award) throws SQLException {
        Connection con = null;
        String query = String.format("INSERT INTO achievements (user_id, achievement_type) VALUES (\'%s\', \'%s\')", userID, award);
        con = pool.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        return new Achievement(userID, award);
    }

    /**
     * Retrieves all achievements for a specific user from the database.
     * @param userId The ID of the user whose achievements to retrieve.
     * @return An ArrayList of Achievement objects earned by the user.
     * @throws SQLException If an SQL exception occurs.
     */
    public ArrayList<Achievement> getAchievements(int userId) throws SQLException {
        ArrayList<Achievement> achievement = new ArrayList<Achievement>();

        Connection con = null;
        try {
            con = pool.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = String.format("SELECT * FROM achievement WHERE user_id = \'%s\'", userId);
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                achievement.add(new Achievement(resultSet.getInt("user_id"),
                        Achievement.Award.values()[resultSet.getInt("achievement_type")]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return achievement;
    }


    /**
     * Checks and creates an achievement for creating a certain number of quizzes.
     * @param userID The ID of the user to check achievements for.
     * @return The created Achievement object if a new achievement is earned, otherwise null.
     * @throws SQLException If an SQL exception occurs.
     */
    public Achievement getCreatedQuizzesAchievement(int userID) throws SQLException {
        Connection con = null;
        try {

            con = pool.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = String.format("SELECT COUNT(CASE WHEN creator = \'%s\' THEN 1 END) as created_quiz_count FROM quizzes", userID);
            ResultSet resultSet = stmt.executeQuery(query);

            if (resultSet.next()) {
                int createdQuizzes = resultSet.getInt(1);

                return switch (createdQuizzes) {
                    case 1 -> createAchievement(userID, Achievement.Award.AMATEUR_AUTHOR);
                    case 5 -> createAchievement(userID, Achievement.Award.PROLIFIC_AUTHOR);
                    case 10 -> createAchievement(userID, Achievement.Award.PRODIGIOUS_AUTHOR);
                    default -> null;
                };
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return null;
    }

    /**
     * Checks and creates an achievement for taking a certain number of quizzes.
     * @param userID The ID of the user to check achievements for.
     * @return The created Achievement object if a new achievement is earned, otherwise null.
     * @throws SQLException If an SQL exception occurs.
     */
    public Achievement getTakenQuizzesAchievement(int userID) throws SQLException {
        Connection con = null;
        try {

            con = pool.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = String.format("SELECT COUNT(CASE WHEN UserID = \'%s\' THEN 1 END) as taken_quiz_count FROM QuizAttempts", userID);
            ResultSet resultSet = stmt.executeQuery(query);

            if (resultSet.next()) {
                int quizzesTaken = resultSet.getInt(1);
                return quizzesTaken == 10 ? createAchievement(userID, Achievement.Award.QUIZ_MACHINE) : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return null;
    }

    /**
     * Checks and creates an achievement for achieving the highest score on a quiz.
     * @param userID The ID of the user to check achievements for.
     * @param score The score achieved by the user.
     * @return The created Achievement object if a new achievement is earned, otherwise null.
     * @throws SQLException If an SQL exception occurs.
     */
    public Achievement getHighestScoreAchievement(int userID, int score) throws SQLException {

        ArrayList<Achievement> achievements = getAchievements(userID);
        for (Achievement a: achievements) {
            if (a.getAward() == Achievement.Award.I_AM_THE_GREATEST)
                return null;
        }

        Connection con = null;
        try {

            con = pool.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = String.format("SELECT MAX(PercentCorrect) FROM QuizAttempts WHERE UserID = \'%s\'", userID);
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                int highScore = resultSet.getInt(1);
                return score >= highScore ? createAchievement(userID, Achievement.Award.I_AM_THE_GREATEST) : null;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return null;
    }
}
