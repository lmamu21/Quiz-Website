package Commons.Interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface IQuestion {


    String getHtmlComponent();
    String getTableName();
    void setCorrectAnswers(List<String> list);
    int check(String answer);
    int getMark();
    int getIndex();
    void setQuizId(int quizId);
    int getQuizId();
    void setId(int id);
    int getId();

    void fillAdditionalData(Connection con);
    PreparedStatement prepareAddStatement(Connection con);
    PreparedStatement prepareAdditionalDataAddStatement(Connection con);
}
