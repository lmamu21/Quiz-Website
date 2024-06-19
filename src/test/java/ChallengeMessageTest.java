import Commons.Message.ChallengeMessage;
import Commons.Message.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeMessageTest {

    private ChallengeMessage challengeMessage;
    private int messageId;
    private int senderId;
    private int recipientId;
    private Timestamp timestamp;
    private String quizLink;
    private int bestScore;

    @BeforeEach
    public void setUp() {
        messageId = 1;
        senderId = 100;
        recipientId = 200;
        timestamp = new Timestamp(System.currentTimeMillis());
        quizLink = "http://example.com/quiz";
        bestScore = 95;
        challengeMessage = new ChallengeMessage(messageId, senderId, recipientId, timestamp, quizLink, bestScore);
    }

    @Test
    public void testGetQuizLink() {
        assertEquals(quizLink, challengeMessage.getQuizLink());
    }

    @Test
    public void testGetBestScore() {
        assertEquals(bestScore, challengeMessage.getBestScore());
    }



    @Test
    public void testInheritedMethods() {
        assertEquals(senderId, challengeMessage.getSender());
        assertEquals(recipientId, challengeMessage.getRecipient());
        assertEquals(timestamp, challengeMessage.getTimestamp());
        assertFalse(challengeMessage.isRead());

        challengeMessage.markAsRead();
        assertTrue(challengeMessage.isRead());
    }
}