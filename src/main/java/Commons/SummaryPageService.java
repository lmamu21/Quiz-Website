package Commons;

import Commons.SummaryPageDAO;
import Commons.SummaryStatistics;
import Commons.QuizAttempt;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class SummaryPageService {
    private SummaryPageDAO summaryPageDAO;

    public SummaryPageService(SummaryPageDAO summaryPageDAO) {
        this.summaryPageDAO = summaryPageDAO;
    }

    public String getQuizDescription(int quizId) throws SQLException {
        return summaryPageDAO.getQuizDescription(quizId);
    }

    public String getCreatorInfo(String quizId) throws SQLException {
        return summaryPageDAO.getCreatorInfo(quizId);
    }

    public String getUsername(int userId) {
        return summaryPageDAO.getUsername(userId);
    }

    public List<QuizAttempt> getUserAttempts(int userId, int quizId, String orderBy) throws SQLException {
        return summaryPageDAO.getUserAttempts(userId, quizId, orderBy);
    }

    public void saveQuizAttempt(QuizAttempt attempt) throws SQLException {
        summaryPageDAO.saveQuizAttempt(attempt);
    }

    public List<QuizAttempt> getRecentTestTakersPerformance(int quizId) throws SQLException {
        return summaryPageDAO.getRecentTestTakersPerformance(quizId);
    }

    public List<QuizAttempt> getHighestPerformersAllTime(int quizId) throws SQLException {
        return summaryPageDAO.getHighestPerformersAllTime(quizId);
    }

    public List<QuizAttempt> getTopPerformersDaily(int quizId) throws SQLException {
        return summaryPageDAO.getTopPerformersDaily(quizId);
    }

    public SummaryStatistics getSummaryStatistics(int quizId) throws SQLException {
        return summaryPageDAO.getSummaryStatistics(quizId);
    }
}