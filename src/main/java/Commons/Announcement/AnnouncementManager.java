/**
 * Manages operations related to announcements.
 * Acts as an intermediary between the application logic and the AnnouncementDAO.
 */
package Commons.Announcement;

import java.sql.SQLException;
import java.util.ArrayList;

public class AnnouncementManager {

    private AnnouncementDAO annDAO; // Data Access Object for handling announcements.

    /**
     * Constructor for AnnouncementManager.
     * @param dao The AnnouncementDAO instance to delegate database operations.
     */
    public AnnouncementManager(AnnouncementDAO dao) {
        annDAO = dao;
    }

    /**
     * Adds a new announcement.
     * @param announcement The content of the announcement to be added.
     * @throws SQLException If an SQL exception occurs.
     */
    public void addAnnouncement(String announcement) throws SQLException {
        annDAO.addAnnouncement(announcement);
    }

    /**
     * Retrieves all announcements.
     * @return An ArrayList of Announcement objects.
     * @throws SQLException If an SQL exception occurs.
     */
    public ArrayList<Announcement> getAnnouncements(int userId) throws SQLException {
        return annDAO.getAnnouncements();
    }

    /**
     * Retrieves the last five announcements.
     * @return An ArrayList of Announcement objects.
     * @throws SQLException If an SQL exception occurs.
     */

    public ArrayList<Announcement> getLastAnnouncements() throws SQLException {
        return annDAO.getLastAnnouncements();
    }
}
