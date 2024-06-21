package Commons;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
     * @param username      - username of user
     * @param password_hash - hash value of password, calculated by SHA-1 algorithm (3rd assignment algo)
     * @param salt
     * @return boolean value - whether entry was inserted into the database
     */
    public boolean addUser(String username, String password_hash, String salt){
        int result = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO users");
        builder.append(" (username, password_hashed, salt) ");
        builder.append(" VALUES ");
        builder.append(String.format("(\'%s\', \'%s\', \'%s\')", username, password_hash, salt));
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
    public String getPasswordHash(String username) throws IllegalArgumentException {
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
            e.printStackTrace();
            throw new IllegalArgumentException();
        }catch(SQLException e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param username    - username of the user
     * @param newPassword - new hashed password
     * @param salt
     * @return boolean - whether entry was updated
     */
    public boolean changePassword(String username, String newPassword, String salt){
        String query = String.format("UPDATE users SET password_hashed = \'%s\', salt = \'%s\' WHERE username = \'%s\'", newPassword, salt, username);
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

    /**
     *
     * @param username username of
     * @return
     */
    public String getSalt(String username) throws IllegalArgumentException {
        String query = String.format("SELECT salt FROM users WHERE username = \'%s\'", username);
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
            e.printStackTrace();
            throw new IllegalArgumentException();
        }catch(SQLException e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
       return result;
    }

    public int getUserId(String username){
        String query = String.format("SELECT user_id FROM users WHERE username  = \'%s\' " , username);
        int result = -1;
        try {
            this.resultSet = stmt.executeQuery(query);

            if(this.resultSet.next()){
                // means that there was entry with provided username
                result = resultSet.getInt(1);
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

    public String getUsername(int userId){
        String query = String.format("SELECT username FROM users WHERE user_id  = \'%d\' " , userId);
        String result = "";
        try {
            this.resultSet = stmt.executeQuery(query);

            if(this.resultSet.next()){
                // means that there was entry with provided userid
                result = resultSet.getString(1);
            }else {
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e) {
            System.out.println("ERROR: Could not find entry with user_id \'" + userId + "\'");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getFriendsNames(String username){
        int id = getUserId(username);
        String query = String.format("SELECT friend_id , status FROM users WHERE user_id  = \'%d\' " , id);
        ArrayList<String> result = new ArrayList<String>();
        try {
            this.resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                if(resultSet.getString(2).equals("active"))
                    result.add(getUsername(resultSet.getInt(1)));
            }
        }catch(Exception e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getFriendRequestUsernames(String username){
        int id = getUserId(username);
        String query = String.format("SELECT user_id , status FROM users WHERE friend_id  = \'%d\' " , id);
        ArrayList<String> result = new ArrayList<String>();
        try {
            this.resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                if(resultSet.getString(2) .equals("pending"))
                    result.add(getUsername(resultSet.getInt(1)));
            }
        }catch(Exception e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public String getQuizName(int id){
        String query = String.format("SELECT quiz_name FROM quizzes WHERE quiz_id  = \'%d\' " , id);
        String result = "";
        try {
            this.resultSet = stmt.executeQuery(query);
            if(this.resultSet.next()){
                // means that there was entry with provided quiz_id
                result = resultSet.getString(1);
            }else {
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e) {
            System.out.println("ERROR: Could not find entry with quiz_id \'" + id + "\'");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Integer> getUsersMadeQuizIds(String username){
        int id = getUserId(username);
        String query = String.format("SELECT quiz_id FROM quizzes WHERE user_id  = \'%d\' " , id);
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            this.resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                result.add(resultSet.getInt(1));
            }
        }catch(Exception e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Integer> getRecentlyAddedQuizzes(int max_count){
        String query = String.format("SELECT quiz_id  FROM quizzes ORDER BY creation_date ");
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            this.resultSet = stmt.executeQuery(query);
            while(resultSet.next() && max_count > 0){
                result.add(resultSet.getInt(1));
                max_count--;
            }
        }catch(Exception e) {
            System.out.println(" ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Integer> getPopularQuizzes(int maxCount){
        String query = String.format("SELECT quiz_id FROM quizzes ORDER BY write_count DESC");
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            this.resultSet = stmt.executeQuery(query);
            while(resultSet.next() && maxCount > 0){
                result.add(resultSet.getInt(1));
                maxCount--;
            }
        }catch(Exception e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
        }
        return result;
    }



}

