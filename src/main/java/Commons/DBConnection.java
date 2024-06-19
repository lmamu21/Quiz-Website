package Commons;

import Commons.Message.ChallengeMessage;
import Commons.Message.FriendRequestMessage;
import Commons.Message.Message;
import Commons.Message.NoteMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnection {
    private Connection con;
    private Statement stmt;
    private ResultSet resultSet;
    private String baseURL = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String password = "admin";
    public DBConnection(String database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(baseURL, user, password);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.execute(String.format("USE %s", database));
        } catch (Exception e) {
            System.out.println("ERROR: Could not connect to database "+database);
            e.printStackTrace();
        }
    }

    /**
     *
     * @param username - username of user
     * @param password_hash - hash value of password, calculated by SHA-1 algorithm (3rd assignment algo)
     * @return boolean value - whether entry was inserted into the database
     */
    public boolean addUser(String username, String password_hash){
        int result = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO users");
        builder.append(" (username, password_hashed) ");
        builder.append(" VALUES ");
        builder.append(String.format("(\'%s\', \'%s\')", username, password_hash));
        try{
            String query = builder.toString();
            result = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (result==0){
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e){
            System.out.println("ERROR: Could not add user \'" + username + "\' to the database");
        }catch(SQLException e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result == 1;
    }

    /**
     *
     * @param username - username of the user
     * @return hash value of password
     */
    public String getPasswordHash(String username) {
        String query = String.format("SELECT password_hashed FROM users WHERE username = \'%s\'", username);
        String result = "";
        try {
            this.resultSet = stmt.executeQuery(query);

            if(this.resultSet.next()){
                // means that there was entry with provided username
                result = resultSet.getString(1);
            }else {
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e) {
            System.out.println("ERROR: Could not find entry with username \'" + username + "\'");
           e.printStackTrace();
        }catch(Exception e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param username - username of the user
     * @param newPassword - new hashed password
     * @return boolean - whether entry was updated
     */
    public boolean changePassword(String username, String newPassword){
        String query = String.format("UPDATE users SET password_hashed = \'%s\' WHERE username = \'%s\'", newPassword, username);
        try{
            int result = stmt.executeUpdate(query);
            if(result==0){
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e){
            System.out.println("ERROR: Could not change password of user \'" + username + "\' to the database");
            e.printStackTrace();
            return false;
        } catch (SQLException e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * @return - number of rows
     */
    public int getCount(){
        int result = 0;
        try{
            this.resultSet = stmt.executeQuery("SELECT * FROM users");
            this.resultSet.last();
            result = this.resultSet.getRow();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public Map<String,User> getUsernamePasswordMap(){
        Map<String,User> mp = new HashMap<String,User>();

        try {
            this.resultSet = stmt.executeQuery("SELECT * FROM users");
            while(resultSet.next()){
                String username = resultSet.getString(1);
                String password = resultSet.getString(2);

                mp.put(username, new User(username,password));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return mp;
    }

    public boolean sendMessage(Message message) {
        PreparedStatement statement = null;
        try {
            int senderId = message.getSender();
            int recipientId = message.getRecipient();
            String messageType = message.getClass().getSimpleName();

            switch (messageType) {
                case "FriendRequestMessage":
                    FriendRequestMessage friendRequestMessage = (FriendRequestMessage) message;
                    statement = con.prepareStatement("INSERT INTO messages (SenderId, RecipientId, MessageType, FriendRequestStatus) VALUES (?, ?, ?, 'pending')");
                    statement.setInt(1, senderId);
                    statement.setInt(2, recipientId);
                    statement.setString(3, messageType);
                    break;
                case "ChallengeMessage":
                    ChallengeMessage challengeMessage = (ChallengeMessage) message;
                    statement = con.prepareStatement("INSERT INTO messages (SenderId, RecipientId, MessageType, QuizLink, ChallengersBestScore) VALUES (?, ?, ?, ?, ?)");
                    statement.setInt(1, senderId);
                    statement.setInt(2, recipientId);
                    statement.setString(3, messageType);
                    statement.setString(4, challengeMessage.getQuizLink());
                    statement.setInt(5, challengeMessage.getBestScore());
                    break;
                case "NoteMessage":
                    NoteMessage noteMessage = (NoteMessage) message;
                    statement = con.prepareStatement("INSERT INTO messages (SenderId, RecipientId, MessageType, NoteText) VALUES (?, ?, ?, ?)");
                    statement.setInt(1, senderId);
                    statement.setInt(2, recipientId);
                    statement.setString(3, messageType);
                    statement.setString(4, noteMessage.getContent());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid message type: " + messageType);
            }

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




//    public List<Message> getUserMessages(int userId){
//        List<Message> messages = new ArrayList<>();
//        PreparedStatement statement = null;
//        ResultSet rs = null;
//        try{
//            String query ="SELECT * from messages Where RecipientId is ?";
//            statement = con.prepareStatement(query);
//            statement.setInt(1,userId);
//            rs = statement.executeQuery();
//            while(rs.next()){
//                int messageId = resultSet.getInt(1);
//                int senderId = resultSet.getInt(2);
//                String messageType = resultSet.getString(3);
//                Timestamp timestamp = resultSet.getTimestamp(4);
//
//
//
//
//                switch (messageType){
//                    case "FriendRequest":
//                        String friendRequestStatus = resultSet.getString("FriendRequestStatus");
//                        messages.add(new FriendRequestMessage(messageId,senderId,userId,timestamp,friendRequestStatus));
//                        break;
//                    case "NoteMessage":
//                        String messageContext = resultSet.getString("NoteText");
//                        messages.add(new NoteMessage(messageId,senderId,userId,timestamp,messageContext));
//                        break;
//                    case "ChallengeMessage":
//                        String quizLink = resultSet.getString("QuizLink");
//                        int bestScore = resultSet.getInt("ChallangerBestScore");
//                        messages.add(new ChallengeMessage(messageId,senderId,userId,timestamp,quizLink,bestScore));
//                        break;
//                    default:
//                        throw new IllegalArgumentException("Invalid message type: " + messageType);
//
//
//                }
//
//            }
//
//
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return messages;
//    }

    public List<FriendRequestMessage> getFriendRequestMessages(int userId) {
        List<FriendRequestMessage> messages = new ArrayList<>();
        String query = "SELECT * from messages Where RecipientId = ? AND MessageType = 'FriendRequest'";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int messageId = rs.getInt(1);
                    int senderId = rs.getInt(2);
                    Timestamp timestamp = rs.getTimestamp(4);
                    String friendRequestStatus = rs.getString("FriendRequestStatus");
                    messages.add(new FriendRequestMessage(messageId, senderId, userId, timestamp, friendRequestStatus));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public List<ChallengeMessage> getChallengeMessages(int userId) {
        List<ChallengeMessage> messages = new ArrayList<>();
        String query = "SELECT * from messages Where RecipientId = ? AND MessageType = 'ChallengeMessage'";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int messageId = rs.getInt(1);
                    int senderId = rs.getInt(2);
                    Timestamp timestamp = rs.getTimestamp(4);
                    String quizLink = rs.getString("QuizLink");
                    int bestScore = rs.getInt("ChallangerBestScore");
                    messages.add(new ChallengeMessage(messageId, senderId, userId, timestamp, quizLink, bestScore));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public List<NoteMessage> getNoteMessages(int userId) {
        List<NoteMessage> messages = new ArrayList<>();
        String query = "SELECT * from messages Where RecipientId = ? AND MessageType = 'NoteMessage'";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int messageId = rs.getInt(1);
                    int senderId = rs.getInt(2);
                    Timestamp timestamp = rs.getTimestamp(4);
                    String messageContent = rs.getString("NoteText");
                    messages.add(new NoteMessage(messageId, senderId, userId, timestamp, messageContent));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}



