/**
 * Data Access Object (DAO) for managing announcements in the database.
 * Provides methods to add new announcements and retrieve existing ones.
 */
package Commons.Announcement;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnnouncementDAO {
    private DataSource pool; // DataSource for managing database connections.
    private String databaseName; // Name of the database being accessed.

    /**
     * Constructor for AnnouncementDAO.
     * @param pool The DataSource to obtain database connections.
     * @param databaseName The name of the database to access.
     */
    public AnnouncementDAO(DataSource pool, String databaseName) {
        this.pool = pool;
        this.databaseName = databaseName;
    }

    /**
     * Adds a new announcement to the database.
     * @param announcement The content of the announcement to be added.
     * @throws SQLException If an SQL exception occurs.
     */
    public void addAnnouncement(String announcement) throws SQLException {
        Connection con = null;
        String query = String.format("INSERT INTO announcement (announcement) VALUES (\'%s\')", announcement);
        con = pool.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * Retrieves all announcements from the database.
     * @return An ArrayList of Announcement objects.
     * @throws SQLException If an SQL exception occurs.
     */
    public ArrayList<Announcement> getAnnouncements() throws SQLException {
        ArrayList<Announcement> announcements = new ArrayList<Announcement>();

        Connection con = null;
        try {
            con = pool.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = "SELECT * FROM announcement";
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("announcementID");
                String announcementText = resultSet.getString("announcement");
                announcements.add(new Announcement(id, announcementText));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return announcements;
    }

    /**
     * Retrieves the last five announcements from the database.
     * @return An ArrayList of Announcement objects.
     * @throws SQLException If an SQL exception occurs.
     */
    public ArrayList<Announcement> getLastAnnouncements() throws SQLException {
        ArrayList<Announcement> announcements = new ArrayList<Announcement>();

        Connection con = null;
        try {
            con = pool.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = "SELECT * FROM announcement ORDER BY announcementID DESC LIMIT 5";
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("announcementID");
                String announcementText = resultSet.getString("announcement");
                announcements.add(new Announcement(id, announcementText));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return announcements;
    }

}
