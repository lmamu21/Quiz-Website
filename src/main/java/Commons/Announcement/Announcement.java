/**
 * Represents an announcement with its ID and content.
 * Provides methods to retrieve the ID and content of the announcement.
 */
package Commons.Announcement;

public class Announcement {
    // The ID of the announcement.
    private int announcementID;
    // The content of the announcement.
    private String announcement;


    /**
     * Constructor to initialize an Announcement object with specified ID and content.
     * @param announcementID The unique identifier for the announcement.
     * @param announcement The content or message of the announcement.
     */
    public Announcement(int announcementID, String announcement) {
        this.announcementID = announcementID;
        this.announcement = announcement;
    }

    /**
     * Retrieves the ID of the announcement.
     * @return The announcement ID.
     */
    public int getAnnouncementID() { return announcementID; }

    /**
     * Retrieves the content of the announcement.
     * @return The announcement content.
     */
    public String getAnnouncement() { return announcement; }
}
