package Commons.Announcement;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnnouncementDAO {
    private DataSource pool;
    private String databaseName;

    public AnnouncementDAO(DataSource pool, String databaseName) {
        this.pool = pool;
        this.databaseName = databaseName;
    }

    public void addAnnouncement(String announcement) throws SQLException {
        Connection con = null;
        String query = String.format("INSERT INTO announcement (announcement) VALUES (\'%s\')", announcement);
        con = pool.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
    }

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
