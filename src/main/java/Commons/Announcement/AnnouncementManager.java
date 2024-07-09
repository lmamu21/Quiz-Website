package Commons.Announcement;

import java.sql.SQLException;
import java.util.ArrayList;

public class AnnouncementManager {

    private AnnouncementDAO annDAO;

    public AnnouncementManager(AnnouncementDAO dao) {
        annDAO = dao;
    }

    public void addAnnouncement(String announcement) throws SQLException {
        annDAO.addAnnouncement(announcement);
    }


    public ArrayList<Announcement> getAnnouncements(int userId) throws SQLException {
        return annDAO.getAnnouncements();
    }

    public ArrayList<Announcement> getLastAnnouncements() throws SQLException {
        return annDAO.getLastAnnouncements();
    }
}
