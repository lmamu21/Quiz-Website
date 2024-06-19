package Commons.Message;

import java.sql.Timestamp;

public class Message {
    protected int messageId;
    protected int senderId;
    protected int recipientId;
    protected Timestamp timestamp;

    protected boolean read;

    public Message(int messageId, int senderId, int recipientId, Timestamp timestamp) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.timestamp = timestamp;
    }

    public int getSender() {
        return senderId;
    }

    public int getRecipient() {
        return recipientId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public boolean isRead() {
        return read;
    }

    public void markAsRead() {
        this.read = true;
    }

    public void displayMessageDetails() {
        System.out.println("From: " + senderId);
        System.out.println("To: " + recipientId);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Read: " + read);
    }



}

