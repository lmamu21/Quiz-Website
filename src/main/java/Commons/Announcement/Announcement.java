package Commons.Announcement;

public class Announcement {
    private int announcementID;
    private String announcement;

    public Announcement(int announcementID, String announcement) {
        this.announcementID = announcementID;
        this.announcement = announcement;
    }

    public int getAnnouncementID() { return announcementID; }

    public String getAnnouncement() { return announcement; }
}
