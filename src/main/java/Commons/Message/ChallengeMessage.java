package Commons.Message;

import Commons.Message.Message;

import java.sql.Timestamp;

public class ChallengeMessage extends Message {
    private String quizLink;
    private int bestScore;

    public ChallengeMessage(int messageId, int senderId, int recipientId, Timestamp timestamp, String quizLink, int bestScore) {
        super(messageId, senderId,recipientId,timestamp);
        this.quizLink = quizLink;
        this.bestScore = bestScore;
    }

    public void displayChallengeDetails() {
        System.out.println("Challenge from: " + getSender());
        System.out.println("Quiz link: " + quizLink);
        System.out.println("Best score: " + bestScore);
    }

    public String getQuizLink() {
        return quizLink;
    }

    public int getBestScore() {
        return bestScore;
    }
}