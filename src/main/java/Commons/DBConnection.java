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
    private String password = "root1234";

    public DBConnection(String database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(baseURL, user, password);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.execute(String.format("USE %s", database));
        } catch (Exception e) {
            System.out.println("ERROR: Could not connect to database " + database);
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
        try {
            String query = builder.toString();
            result = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (result==0){
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Could not add user \'" + username + "\' to the database");
        } catch (SQLException e) {
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

            if (this.resultSet.next()) {
                // means that there was entry with provided username
                result = resultSet.getString(1);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Could not find entry with username \'" + username + "\'");
            e.printStackTrace();
        } catch (Exception e) {
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
    public boolean changePassword(String username, String newPassword, String salt) {
        String query = String.format("UPDATE users SET password_hashed = \'%s\', salt = \'%s\' WHERE username = \'%s\'", newPassword, salt, username);
        try {
            int result = stmt.executeUpdate(query);
            if (result == 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Could not change password of user \'" + username + "\' to the database");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.out.println("ERROR: SQL connection error");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @return - number of rows
     */
    public int getCount() {
        int result = 0;
        try {
            this.resultSet = stmt.executeQuery("SELECT * FROM users");
            this.resultSet.last();
            result = this.resultSet.getRow();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param sender
     * @param receiver
     */
    public boolean sendFriendRequest(String sender, String receiver) {
        String query = String.format("INSERT INTO friends VALUES (\'%s\', \'%s\', \'%s\')", getID(sender), getID(receiver), "pending");
        int result = 0;
        boolean senderExists = accountExists(sender);
        boolean receiverExists = accountExists(receiver);
        if (senderExists && receiverExists) {
            try {
                result = stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("One of the provided accounts does not exists");
        }
        return result == 1;
    }

    public boolean acceptFriendRequest(String accepter, String requester) {
        boolean requestExists = false;
        String query = String.format("SELECT * FROM friends WHERE user_id = \'%s\' AND friend_id = \'%s\' AND friendship_status = \'pending\'", getID(requester), getID(accepter));
        try {
            resultSet = stmt.executeQuery(query);
            requestExists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!requestExists) {
            return false;
        }

        String updateQuery = String.format("UPDATE friends SET friendship_status = \'friends\' WHERE user_id = \'%s\' and friend_id = \'%s\'", getID(requester), getID(accepter), "friends");
        try {
            int rowsAffected = stmt.executeUpdate(updateQuery);
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateQuery = String.format("INSERT INTO friends VALUES (\'%s\', \'%s\', \'%s\')", getID(accepter), getID(requester), "friends");
        try {
            int rowsAffected = stmt.executeUpdate(updateQuery);
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private boolean accountExists(String username) {
        String query = String.format("SELECT * FROM users WHERE username = \'%s\'", username);
        try {
            resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public String getID(String username) {
        String query = String.format("SELECT user_id FROM users WHERE username = \'%s\'", username);
        String result = "";
        try {
            resultSet = stmt.executeQuery(query);
            resultSet.next();
            result = String.valueOf(resultSet.getInt("user_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
    }

    /**
     * @param username username of
     * @return
     */
    public String getSalt(String username) {
        String query = String.format("SELECT salt FROM users WHERE username = \'%s\'", username);
        String result = "";
        try {
            this.resultSet = stmt.executeQuery(query);

            if (this.resultSet.next()) {
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





}

