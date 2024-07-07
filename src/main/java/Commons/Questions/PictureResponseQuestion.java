package Commons.Questions;

import Commons.Interfaces.IQuestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PictureResponseQuestion implements IQuestion {
    public static final String tableName = "picture_response";

    private int Id;
    private int quizId;
    private int index;
    private String ImgUrl;
    private List<String> correctAnswers;
    private int Mark;

    //for constructing from jsp file
    public PictureResponseQuestion(int index, String ImgURl , List<String> correctAnswers , int Mark ){
        this.Id = 0;
        this.index = index;
        this.ImgUrl = ImgURl;
        this.correctAnswers = correctAnswers;
        this.Mark = Mark;
    }
    //for constructing from database
    public PictureResponseQuestion(int Id ,int quizId, int index, String ImgUrl , int Mark ){
        this.Id = Id;
        this.quizId = quizId;
        this.index = index;
        this.ImgUrl = ImgUrl;
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
                "            <div class = \"question_div\">\n" +
                "                <img src = \" " +ImgUrl + "\">\n" +
                "            </div>\n" +
                "\n" +
                "            <div class = \"answer_div\">\n" +
                "                    <label class = \"fill-in\">\n" +
                "                        <input type=\"text\" name=\""+ index +"\" >\n" +
                "                    </label>\n" +
                "            </div>\n" +
                "     </section>\n";
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
        //nothing here
    }

    @Override
    public PreparedStatement prepareAddStatement(Connection con) {
        String query = "INSERT INTO " + tableName + " (quiz_id, question_index, img_url, mark) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, quizId);
            ps.setInt(2, index);
            ps.setString(3, ImgUrl);
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
