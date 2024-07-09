/**
 * Represents an achievement earned by a user.
 * Each achievement is associated with a specific user ID and award type.
 */
package Commons.Achievement;

import java.util.HashMap;
import java.util.Map;
public class Achievement {
    private int userID;
    private Award award;

    //    Achievement Types
//    Amateur Author — The user created a quiz.
//    Prolific Author — The user created five quizzes.
//    Prodigious Author — The user created ten quizzes.
//    Quiz Machine — The user took ten quizzes.
//    I am the Greatest — The user had the highest score on a quiz. Note, once earned this
//    achievement does not go away if someone else later bests the high score.
//    Practice Makes Perfect — The user took a quiz in practice mode.

    /**
     * Enum defining various types of achievements.
     */
    public enum Award {
        AMATEUR_AUTHOR,
        PROLIFIC_AUTHOR,
        PRODIGIOUS_AUTHOR,
        QUIZ_MACHINE,
        I_AM_THE_GREATEST,
        PRACTICE_MAKES_PERFECT
    }

    // Mapping from Award enum to corresponding achievement text.
    private static final Map<Award, String> achievementTexts = new HashMap<>();

    static {
        achievementTexts.put(Award.AMATEUR_AUTHOR, "Amateur Author");
        achievementTexts.put(Award.PROLIFIC_AUTHOR, "Prolific Author");
        achievementTexts.put(Award.PRODIGIOUS_AUTHOR, "Prodigious Author");
        achievementTexts.put(Award.QUIZ_MACHINE, "Quiz Machine");
        achievementTexts.put(Award.I_AM_THE_GREATEST, "I Am The Greatest");
        achievementTexts.put(Award.PRACTICE_MAKES_PERFECT, "Practice Makes Perfect");
    }

    /**
     * Constructs an Achievement object with specified userID and award type.
     * @param userID The user ID associated with the achievement.
     * @param award The type of award earned.
     */
    public Achievement(int userID, Award award) {
        this.userID = userID;
        this.award = award;
    }

    /**
     * Retrieves the user ID associated with this achievement.
     * @return The user ID.
     */
    public int getUserID() { return userID; }

    /**
     * Retrieves the type of award earned.
     * @return The award type.
     */
    public Award getAward() { return award; }

    /**
     * Retrieves the textual representation of the achievement.
     * @return The textual representation of the achievement.
     */
    public String getAchievement() {
        return achievementTexts.getOrDefault(award, "");
    }
}
