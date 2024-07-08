package Commons;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDao {

    private Connection con;
    private Statement stmt;
    private ResultSet resultSet;
    private DataSource pool;
    private String databaseName;

    public FriendRequestDao(DataSource pool, String databaseName) {
        this.pool = pool;
        this.databaseName = databaseName;
    }
   
    public synchronized void  addFriendRequest(FriendRequest request){

        con = null;

        try {
            con = pool.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "INSERT INTO friend_request (from_username, to_username, status) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,request.getFrom());
            preparedStatement.setString(2,request.getTo());
            preparedStatement.setString(3,request.getStatus().name());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (con != null) try {
                // Returns the connection to the pool.
                con.close();
            } catch (Exception ignored) {
            }
        }


    }
    public synchronized void deleteFriendRequest(int requestId) {
        con = null;

        try {
            con = pool.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "DELETE FROM friend_request WHERE id = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, requestId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (con != null) try {
                // Returns the connection to the pool.
                con.close();
            } catch (Exception ignored) {
            }
        }
    }
    public synchronized List<FriendRequest> getFriendRequestsTo(String username){
        con = null;
        List<FriendRequest> requests = new ArrayList<FriendRequest>();
        try {
            con = pool.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "Select * from friend_requests WHERE to_username = " + username ;


            resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                requests.add(fetchFriendRequest());
            }

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (con != null) try {
                // Returns the connection to the pool.
                con.close();
            } catch (Exception ignored) {
            }
        }
        return requests;
    }
    public synchronized FriendRequest getFriendRequestsBetween(String user1 , String user2){
        con = null;
        FriendRequest request =null;
        try {
            con = pool.getConnection();
            String query = "Select * from friend_requests WHERE to_username = " + user1 + " and from_username =" + user2 ;
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = stmt.executeQuery(query);
            if(resultSet != null)
                request = fetchFriendRequest();

        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (con != null) try {
                // Returns the connection to the pool.
                con.close();
            } catch (Exception ignored) {
            }
        }
        return request;
    }

    private FriendRequest fetchFriendRequest(){
        FriendRequest request = null;
        try{
            int id = resultSet.getInt("id");
            String from = resultSet.getString("from_username");
            String to = resultSet.getString("to_username");
            FriendRequest.Status  status = FriendRequest.Status.valueOf(resultSet.getString("status"));
            request = new FriendRequest(id,from,to,status);
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }

}
