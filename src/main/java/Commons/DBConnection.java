package Commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private String baseURL = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String password = "admin";
    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(baseURL, user, password);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public static void main(String[] args){
        DBConnection db = new DBConnection();
    }
}

