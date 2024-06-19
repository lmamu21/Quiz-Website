package Commons.Message;

import java.sql.Timestamp;

public class NoteMessage extends Message {
    private String content;

    public NoteMessage(int messageId, int senderId, int recipientId, Timestamp timestamp, String content) {
        super(messageId, senderId,recipientId,timestamp);
        this.content = content;
    }

    public void displayNoteDetails() {
        System.out.println("Note from: " + getSender());
        System.out.println("Content: " + content);
    }

    public String getContent() {
        return content;
    }
}