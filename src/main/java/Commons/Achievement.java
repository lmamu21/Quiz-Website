package Commons;

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
    public enum Award {
        AMATEUR_AUTHOR,
        PROLIFIC_AUTHOR,
        PRODIGIOUS_AUTHOR,
        QUIZ_MACHINE,
        I_AM_THE_GREATEST,
        PRACTICE_MAKES_PERFECT
    }

    private static final Map<Award, String> achievementTexts = new HashMap<>();

    static {
        achievementTexts.put(Award.AMATEUR_AUTHOR, "Amateur Author");
        achievementTexts.put(Award.PROLIFIC_AUTHOR, "Prolific Author");
        achievementTexts.put(Award.PRODIGIOUS_AUTHOR, "Prodigious Author");
        achievementTexts.put(Award.QUIZ_MACHINE, "Quiz Machine");
        achievementTexts.put(Award.I_AM_THE_GREATEST, "I Am The Greatest");
        achievementTexts.put(Award.PRACTICE_MAKES_PERFECT, "Practice Makes Perfect");
    }

    public Achievement(int userID, Award award) {
        this.userID = userID;
        this.award = award;
    }

    public int getUserID() { return userID; }
    public Award getAward() { return award; }
    public String getAchievement() {
        return achievementTexts.getOrDefault(award, "");
    }
}
