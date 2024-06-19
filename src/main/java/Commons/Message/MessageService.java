package Commons.Message;

import Commons.DBConnection;

import java.util.List;

public class MessageService {
    DBConnection dbCon;

    public MessageService(String database){
        dbCon = new DBConnection(database);
    }
    public boolean sendMessage(Message message){
        try {
            return dbCon.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<FriendRequestMessage> getUserFriendRequestMessages(int userId){
        try {
            return dbCon.getFriendRequestMessages(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<NoteMessage> getUserNoteMessages(int userId){
        try {
            return dbCon.getNoteMessages(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ChallengeMessage> getUserChallengeMessages(int userId){
        try {
            return dbCon.getChallengeMessages(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
