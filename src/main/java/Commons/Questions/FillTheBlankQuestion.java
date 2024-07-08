package Commons.Questions;

import Commons.Interfaces.IQuestion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FillTheBlankQuestion implements IQuestion {
    public static final String tableName = "fill_the_blank";

    private int Id;
    private int quizId;
    private int index;
    private String questionHead;
    private String questionTail;
    private List<String> correctAnswers;
    private int Mark;

    //for constructing from jsp file
    public FillTheBlankQuestion(int index, String questionHead , String questionTail, List<String> correctAnswers , int Mark ){
        this.Id = 0;
        this.quizId = 0;
        this.index = index;
        this.questionHead = questionHead;
        this.questionTail = questionTail;
        this.correctAnswers = correctAnswers;
        this.Mark = Mark;
    }
    //for constructing from database
    public FillTheBlankQuestion(int Id ,int quizId, int index, String questionHead,String questionTail, int Mark ){
        this.Id = Id;
        this.quizId = quizId;
        this.index = index;
        this.questionHead = questionHead;
        this.questionTail = questionTail;
        this.Mark = Mark;
    }

    @Override
    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
    @Override
    public String getHtmlComponent() {
        String out = " <section class = \"question_section\">\n" +
                "            <div class = \"question_num\">\n" +
                "                <p>\n" +
                 index +
                "                </p>\n" +
                "            </div>\n" +
                "<div class = \"question_div\">\n" +
                "<p> " + questionHead + " </p>\n" +
                "\n" +
                "                    <label class = \"fill-in\">\n" +
                "                        <input type=\"text\" name=\""+ index +"\" >\n" +
                "                    </label>\n" +
                "<p> " + questionTail+ " </p>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class = \"answer_div\">\n" +

                "            </div>\n" +
                "</section >";
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
        String query = "SELECT correct_answer FROM " + tableName+"_correct_answer"+  " WHERE question_id = " + Id;
        correctAnswers = new ArrayList<String>();
        try  {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                correctAnswers.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public PreparedStatement prepareAddStatement(Connection con) {
        String query = "INSERT INTO " + tableName + " (quiz_id, question_index, question_head, question_tail, mark) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, quizId);
            ps.setInt(2, index);
            ps.setString(3, questionHead);
            ps.setString(4, questionTail);
            ps.setInt(5, Mark);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ps;
    }

    @Override
    public PreparedStatement prepareAdditionalDataAddStatement(Connection con) {
        PreparedStatement ps = null;
        try {

            for (String correctAnswer : correctAnswers) {
                String correctQuery = "INSERT INTO "+tableName+"_correct_answer"+" (question_id,correct_answer) VALUES (?, ?) ";
                ps = con.prepareStatement(correctQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, Id);
                ps.setString(2, correctAnswer);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ps;
    }

}
