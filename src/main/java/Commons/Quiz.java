package Commons;

import java.util.ArrayList;

public class Quiz {
    private int quizID;
    private ArrayList<QuizOptions> quizOptions;
    private String quizName;
    //    The text description of the quiz.
    private String quizDescription;
    //    The creatorID of the quiz (hot linked to the creator’s user page).
    private int creatorID;

    private ArrayList<Question> questions;

    //    Quiz Options
//    Random Questions — Allow the creatorID to set the quiz to either randomize the order of the questions or to
//    always present them in the same order.
//    One Page vs. Multiple Pages — Allow the quiz writer to determine if all the questions should appear on a single
//    webpage, with a single submit button, or if the quiz should display a single question allow the user
//    to submit the answer, then display another question.
//    Immediate Correction — For multiple page quizzes, this setting determines whether the user will receive immediate
//    feedback on an answer, or if the quiz will only be graded once all the questions have been seen and responded to.
    public enum QuizOptions {
        RANDOM_QUESTIONS,
        ONE_PAGE,
        MULTIPLE_PAGES,
        IMMEDIATE_CORRECTION
    }

    public Quiz(int quizID, ArrayList<QuizOptions> quizOptions, String quizName, String quizDescription, int creatorID,
                ArrayList<Question> questions) {
        this.quizID = quizID;
        this.quizOptions = quizOptions;
        this.quizName = quizName;
        this.quizDescription = quizDescription;
        this.questions = questions;
        this.creatorID = creatorID;
    }

    public int getQuizID() {
        return quizID;
    }
    public ArrayList<QuizOptions> getQuizOptions() {
        return quizOptions;
    }
    public String getQuizName() {
        return quizName;
    }
    public String getQuizDescription() {
        return quizDescription;
    }
    public int creatorID() {
        return creatorID;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}