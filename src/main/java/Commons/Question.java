package Commons;

import java.util.ArrayList;

public class Question {
    private int questionID;
    private QuestionType questionType;
    private String question;
    private int questionIndex;
    private String url;
    private ArrayList<Answer> answers;
    private int mark;

    //    Question Types
//    Question-Response — This is a standard text question with an appropriate text response.
//    Fill in the Blank — his is similar to standard web.Question-Response, except a blank can go anywhere within a question.
//    Multiple Choice — Allow the user to select from one of a number of possible provided answers.
//    Picture-Response Questions — In a picture response question, the system will display an image,
//    and the user will provide a text response to the image.
    public enum QuestionType {
        QUESTION_RESPONSE,
        FILL_IN_THE_BLANK,
        MULTIPLE_CHOICE,
        PICTURE_RESPONSE
    }

    public Question(int questionID, QuestionType questionType, String question,
                    int questionIndex, String url, ArrayList<Answer> answers, int mark) {
        this.questionID = questionID;
        this.questionType = questionType;
        this.question = question;
        this.questionIndex = questionIndex;
        this.url = url;
        this.answers = answers;
        this.mark = mark;
    }

    public int getQuestionID() {
        return questionID;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public int getMark() {
        return mark;
    }


}