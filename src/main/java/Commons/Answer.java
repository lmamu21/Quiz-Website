package Commons;

public class Answer {
    int answerID;
    String answer;
    boolean isCorrect;

    public Answer(int answerID, String answer, boolean isCorrect) {
        this.answerID = answerID;
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

}
