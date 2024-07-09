package Commons;

import Commons.Interfaces.IQuestion;

import java.util.ArrayList;

public class Quiz {
    private int quizID;
    private ArrayList<QuizOptions> quizOptions;
    private String quizName;
    //    The text description of the quiz.
    private String quizDescription;
    //    The creator of the quiz (hot linked to the creator’s user page).
    private int creator;

    private ArrayList<IQuestion> Questions;



    //    Quiz Options
//    Random Questions — Allow the creator to set the quiz to either randomize the order of the questions or to
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

    /**
     * Constructor to initialize a Quiz object with all attributes.
     * @param quizID The unique identifier for the quiz.
     * @param quizOptions List of options/settings for the quiz.
     * @param quizName The name of the quiz.
     * @param quizDescription Text description of the quiz.
     * @param creator The ID of the quiz creator.
     * @param questions List of questions in the quiz.
     */
    public Quiz(int quizID, ArrayList<QuizOptions> quizOptions, String quizName, String quizDescription, int creator,
                ArrayList<IQuestion> questions) {
        this.quizID = quizID;
        this.quizOptions = quizOptions;
        this.quizName = quizName;
        this.quizDescription = quizDescription;
        this.Questions = questions;
        this.creator = creator;
    }

    /**
     * Constructor to initialize a Quiz object with basic attributes.
     * @param quizID The unique identifier for the quiz.
     * @param quizName The name of the quiz.
     * @param quizDescription Text description of the quiz.
     * @param creator The ID of the quiz creator.
     */
    public Quiz(int quizID, String quizName, String quizDescription, int creator){
        this.quizID = quizID;
        this.quizName = quizName;
        this.quizDescription = quizDescription;
        this.creator = creator;
    }

    /**
     * Retrieves the unique identifier for the quiz.
     * @return The quiz ID.
     */
    public int getQuizID() {
        return quizID;
    }

    /**
     * Retrieves the list of options/settings for the quiz.
     * @return The list of quiz options.
     */
    public ArrayList<QuizOptions> getQuizOptions() {
        return quizOptions;
    }

    /**
     * Retrieves the name of the quiz.
     * @return The quiz name.
     */
    public String getQuizName() {
        return quizName;
    }

    /**
     * Retrieves the text description of the quiz.
     * @return The quiz description.
     */
    public String getQuizDescription() {
        return quizDescription;
    }

    /**
     * Retrieves the ID of the quiz creator.
     * @return The creator ID.
     */
    public int getCreatorID() {
        return creator;
    }

    /**
     * Retrieves the list of questions in the quiz.
     * @return The list of questions.
     */
    public ArrayList<IQuestion> getQuestions() {
        return Questions;
    }

    /**
     * Sets the list of options/settings for the quiz.
     * @param quizOptions The list of quiz options to set.
     */
    public void setQuizOptions(ArrayList<QuizOptions> quizOptions) {
        this.quizOptions = quizOptions;
    }

    /**
     * Sets the list of questions in the quiz.
     * @param questions The list of questions to set.
     */
    public void setQuestions(ArrayList<IQuestion> questions) {
        Questions = new ArrayList<>(questions);
    }

}