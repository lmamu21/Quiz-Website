package Commons.Achievement;

import java.sql.SQLException;
import java.util.ArrayList;

public class AchievementManager {

    private AchievementDAO achDAO;

    public AchievementManager(AchievementDAO dao) {
        achDAO = dao;
    }

    public ArrayList<Achievement> getAchievements(int userId) throws SQLException {
        return achDAO.getAchievements(userId);
    }

    public Achievement createAchievement(int userID, Achievement.Award award) throws SQLException {
        return achDAO.createAchievement(userID, award);
    }

    public Achievement getCreatedQuizzesAchievement(int userID) throws SQLException {
        return achDAO.getCreatedQuizzesAchievement(userID);
    }

    public Achievement getTakenQuizzesAchievement(int userID) throws SQLException {
        return achDAO.getTakenQuizzesAchievement(userID);
    }

    public Achievement getHighestScoreAchievement(int userID, int score) throws SQLException {
        return achDAO.getHighestScoreAchievement(userID, score);
    }

}