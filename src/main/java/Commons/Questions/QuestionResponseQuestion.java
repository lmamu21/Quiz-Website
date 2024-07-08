package Commons.Questions;

import Commons.Interfaces.IQuestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuestionResponseQuestion implements IQuestion {
    public static final String tableName = "question_response";

    private int Id;
    private int quizId;
    private int index;
    private String question;
    private List<String> correctAnswers;
    private int Mark;

    //for constructing from jsp file
    public QuestionResponseQuestion(int index, String question , List<String> correctAnswers , int Mark ){
        this.Id = 0;
        this.index = index;
        this.question = question;
        this.correctAnswers = correctAnswers;
        this.Mark = Mark;
    }
    //for constructing from database
    public QuestionResponseQuestion(int Id ,int quizId, int index, String question, int Mark ){
        this.Id = Id;
        this.quizId = Id;
        this.index = index;
        this.question = question;
        this.Mark = Mark;
    }
    @Override
    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
    @Override
    public String getHtmlComponent() {
        String out = "<div class = \"question_div\">\n" +
                "<p> " + question + " </p>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class = \"answer_div\">\n" +
                "\n" +
                "                    <label class = \"fill-in\">\n" +
                "                        <input type=\"text\" name=\" "+ index +" \" >\n" +
                "                    </label>\n" +
                "            </div>";
        return out;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public int check(String answer) {
        for(String var : correctAnswers) {
            if (answer.equals(var))
                return Mark;
        }
        return 0;
    }

    @Override
    public int getMark() {
        return Mark;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public int getQuizId() {
        return quizId;
    }

    @Override
    public void setId(int id) {
        Id = id;
    }

    @Override
    public int getId() {
        return Id;
    }

    @Override
    public void fillAdditionalData(Connection con) {
        // nothing here
    }


    @Override
    public PreparedStatement prepareAddStatement(Connection con) {
        String query = "INSERT INTO " + tableName + " (quiz_id, question_index, question, mark) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, quizId);
            ps.setInt(2, index);
            ps.setString(3, question);
            ps.setInt(4, Mark);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ps;
    }

    @Override
    public PreparedStatement prepareAdditionalDataAddStatement(Connection con) {
        return null;
    }


}
