/**
 * Manages operations related to achievements for users.
 * Acts as an intermediary between the application logic and the AchievementDAO.
 */
package Commons.Achievement;

import java.sql.SQLException;
import java.util.ArrayList;

public class AchievementManager {

    // Data Access Object for handling achievements.
    private AchievementDAO achDAO;

    /**
     * Constructor for AchievementManager.
     * @param dao The AchievementDAO instance to delegate database operations.
     */
    public AchievementManager(AchievementDAO dao) {
        achDAO = dao;
    }

    /**
     * Retrieves all achievements for a specific user.
     * @param userId The ID of the user whose achievements to retrieve.
     * @return An ArrayList of Achievement objects earned by the user.
     * @throws SQLException If an SQL exception occurs.
     */
    public ArrayList<Achievement> getAchievements(int userId) throws SQLException {
        return achDAO.getAchievements(userId);
    }

    /**
     * Creates a new achievement for a user.
     * @param userID The ID of the user earning the achievement.
     * @param award The type of achievement being earned.
     * @return The created Achievement object.
     * @throws SQLException If an SQL exception occurs.
     */
    public Achievement createAchievement(int userID, Achievement.Award award) throws SQLException {
        return achDAO.createAchievement(userID, award);
    }

    /**
     * Retrieves the achievement for creating a certain number of quizzes by a user.
     * @param userID The ID of the user to check achievements for.
     * @return The created Achievement object if a new achievement is earned, otherwise null.
     * @throws SQLException If an SQL exception occurs.
     */
    public Achievement getCreatedQuizzesAchievement(int userID) throws SQLException {
        return achDAO.getCreatedQuizzesAchievement(userID);
    }

    /**
     * Retrieves the achievement for taking a certain number of quizzes by a user.
     * @param userID The ID of the user to check achievements for.
     * @return The created Achievement object if a new achievement is earned, otherwise null.
     * @throws SQLException If an SQL exception occurs.
     */
    public Achievement getTakenQuizzesAchievement(int userID) throws SQLException {
        return achDAO.getTakenQuizzesAchievement(userID);
    }

    /**
     * Retrieves the achievement for achieving the highest score on a quiz by a user.
     * @param userID The ID of the user to check achievements for.
     * @param score The score achieved by the user.
     * @return The created Achievement object if a new achievement is earned, otherwise null.
     * @throws SQLException If an SQL exception occurs.
     */
    public Achievement getHighestScoreAchievement(int userID, int score) throws SQLException {
        return achDAO.getHighestScoreAchievement(userID, score);
    }

}