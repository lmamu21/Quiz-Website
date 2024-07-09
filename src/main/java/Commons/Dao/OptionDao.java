package Commons.Dao;

import Commons.Quiz;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionDao {

    private Connection con;
    private Statement stmt;
    private ResultSet resultSet;
    private DataSource pool;
    private String databaseName;

    public OptionDao(DataSource _pool, String _databaseName ) {
        this.pool = _pool;
        this.databaseName = _databaseName;

    }


    private synchronized Quiz.QuizOptions fetchOption(ResultSet resultSet) throws SQLException {
        int optionId = resultSet.getInt("option_id");
        int quizId = resultSet.getInt("quiz_id");
        String optionName = resultSet.getString("option_name");
        return Quiz.QuizOptions.valueOf(optionName);
    }

    public synchronized void addOption(Quiz.QuizOptions opt, int quiz_id) {
        String query = "INSERT INTO quiz_options (quiz_id, option_name) VALUES (?, ?)";
        try {
            Connection con = pool.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, quiz_id);
            pstmt.setString(2, opt.name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public synchronized List<Quiz.QuizOptions> getOptions(int quiz_id){
        con = null;
        ArrayList<Quiz.QuizOptions> res = new ArrayList<Quiz.QuizOptions>();
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            String query = String.format("SELECT * FROM quiz_options WHERE quiz_id = \'%d\' ",quiz_id );
            resultSet=stmt.executeQuery(query);
            while(resultSet.next()){
                res.add(fetchOption(resultSet));
            }
            stmt.close();
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            if (con != null) try {
                // Returns the connection to the pool.
                con.close();
            } catch (Exception ignored) {
            }
        }
        return res;
    }


}
