package Commons.Message;

import Commons.Message.Message;

import java.sql.Timestamp;

public class FriendRequestMessage extends Message {
    String friendRequestStatus = null;

    public FriendRequestMessage(int messageId, int senderId, int recipientId, Timestamp timestamp, String friendRequestStatus) {
        super(messageId, senderId, recipientId, timestamp);
        this.friendRequestStatus = friendRequestStatus;
    }

    public void acceptFriendRequest() {
        //TODO  Logic to accept friend request
        System.out.println("Friend request accepted.");
        //TODO Update system as appropriate
    }

    public void rejectFriendRequest() {
        //TODO Logic to reject friend request
        System.out.println("Friend request rejected.");
        //TODO Update system as appropriate
    }
}