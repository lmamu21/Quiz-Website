package Commons;

import java.math.BigDecimal;
import java.sql.Time;

public class SummaryStatistics {
    private int quizId;
    private BigDecimal averageScore;
    private BigDecimal highestScore;
    private BigDecimal lowestScore;
    private int totalAttempts;
    private Time averageTimeSpent;

    public SummaryStatistics(int quizId,BigDecimal averageScore, BigDecimal highestScore, BigDecimal lowestScore, int totalAttempts,Time averageTimeSpent) {
        this.quizId = getQuizId();
        this.averageScore = averageScore;
        this.highestScore = highestScore;
        this.lowestScore = lowestScore;
        this.totalAttempts = totalAttempts;
        this.averageTimeSpent = averageTimeSpent;
    }
    public int getQuizId() {
        return quizId;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public BigDecimal getHighestScore() {
        return highestScore;
    }

    public BigDecimal getLowestScore() {
        return lowestScore;
    }

    public Time getAverageTimeSpent() {
        return averageTimeSpent;
    }

    @Override
    public String toString() {
        return "SummaryStatistics{" +
                "quizId=" + quizId +
                ", totalAttempts=" + totalAttempts +
                ", averageScore=" + averageScore +
                ", highestScore=" + highestScore +
                ", lowestScore=" + lowestScore +
                ", averageTimeSpent=" + averageTimeSpent +
                '}';
    }
}
