package Commons;

public class Answer {
    int answerID;
    int questionId;
    String answer;
    boolean isCorrect;
    public Answer(int answerID, int questionId, String answer, boolean isCorrect) {
        this.answerID = answerID;
        this.questionId = questionId;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public int getAnswerID() {
        return answerID;
    }
    public String getAnswer() {
        return answer;
    }
    public boolean isCorrect() {
        return isCorrect;
    }

    public int getQuestionId() {
        return questionId;
    }
}
