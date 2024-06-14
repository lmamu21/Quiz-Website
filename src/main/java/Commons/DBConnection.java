package Commons;

import java.sql.*;

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
}

