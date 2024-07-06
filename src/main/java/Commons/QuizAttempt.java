package Commons;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

public class QuizAttempt {
    private int attemptId;
    private int quizId;
    private int userId;
    private Timestamp attemptDate;

    private Time timeTaken;
    private BigDecimal percentCorrect;


    public QuizAttempt(int attemptId, int quizId, int userId, Timestamp attemptDate, Time timeTaken, BigDecimal percentCorrect) {
        this.attemptId = attemptId;
        this.quizId = quizId;
        this.userId = userId;
        this.attemptDate = attemptDate;
        this.timeTaken = timeTaken;
        this.percentCorrect = percentCorrect;
    }


    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(Timestamp attemptDate) {
        this.attemptDate = attemptDate;
    }

    public Time getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Time timeTaken) {
        this.timeTaken = timeTaken;
    }

    public BigDecimal getPercentCorrect() {
        return percentCorrect;
    }

    public void setPercentCorrect(BigDecimal percentCorrect) {
        this.percentCorrect = percentCorrect;
    }

    public boolean isValid() {
        return quizId > 0 && userId > 0 && percentCorrect != null && percentCorrect.compareTo(BigDecimal.ZERO) >= 0 && percentCorrect.compareTo(BigDecimal.valueOf(100)) <= 0;
    }

    // Format the attempt data as a string
    @Override
    public String toString() {
        return "QuizAttempt{" +
                "attemptId=" + attemptId +
                ", quizId=" + quizId +
                ", userId=" + userId +
                ", attemptDate=" + attemptDate +
                ", timeTaken=" + timeTaken +
                ", percentCorrect=" + percentCorrect +
                '}';
    }

    public int compareByPercentCorrect(QuizAttempt other) {
        return this.percentCorrect.compareTo(other.percentCorrect);
    }

    public int compareByTimeTaken(QuizAttempt other) {
        return this.timeTaken.compareTo(other.timeTaken);
    }
}
