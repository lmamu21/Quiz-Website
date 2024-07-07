package Commons.Questions;

import Commons.Interfaces.IQuestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestion implements IQuestion {
    public static final String tableName = "multiple_choice";
    private static final String OptionAnswersTableName = "multiple_choice_options";

    private int Id;
    private int quizId;
    private int index;
    private String question;
    private List<String> OptionAnsers;
    private List<String> correctAnswers;
    private int Mark;

    //for constructing from jsp file
    public MultipleChoiceQuestion(int index, String question , List<String> OptionAnswers , List<String> correctAnswers , int Mark ){
        this.Id = 0;
        this.index = index;
        this.OptionAnsers = OptionAnswers;
        this.question = question;
        this.correctAnswers = correctAnswers;
        this.Mark = Mark;
    }
    //for constructing from database
    public MultipleChoiceQuestion(int Id , int quizId , int index, String question, int Mark ){
        this.Id = Id;
        this.quizId = quizId;
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
        String out = " <section class = \"question_section\">\n" +
                "            <div class = \"question_num\">\n" +
                "                <p>\n" +
                index +
                "                </p>\n" +
                "            </div>\n" +
                "<div class = \"question_div\">\n" +
                "<p> " + question + " </p>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class = \"answer_div\">\n";
        for(String var : OptionAnsers){
            out += "<label class = \"select-answer\">\n" +
                    "                        <input type=\"radio\" name=\""+index+"\" value=\""+var+"\"> " +var + "\n" +
                    "                    </label>\n";
        }

        out += "            </div>\n" +
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
        String query = "INSERT INTO " + OptionAnswersTableName + " (question_id, option_answer) VALUES (?, ?)";
        try  {
            PreparedStatement ps = con.prepareStatement(query);

            for (String optionAnswer : OptionAnsers) {
                ps.setInt(1, Id);
                ps.setString(2, optionAnswer);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                            String query = "INSERT INTO " + OptionAnswersTableName + " (question_id, option_answer) VALUES (?, ?)";
        PreparedStatement ps = null;
        try {
            for (String optionAnswer : OptionAnsers) {
                ps = con.prepareStatement(query);
                ps.setInt(1, Id);
                ps.setString(2, optionAnswer);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ps;
    }

}
