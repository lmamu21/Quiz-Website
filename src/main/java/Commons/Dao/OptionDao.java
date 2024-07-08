package Commons.Dao;

import Commons.Quiz;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    private synchronized Quiz.QuizOptions fetchOption() {
        return null;
    }

    public synchronized void addOption(Quiz.QuizOptions opt, int quiz_id){

    }
    public synchronized List<Quiz.QuizOptions> getOptions(int quiz_id){
        con = null;
        ArrayList<Quiz.QuizOptions> res = new ArrayList<Quiz.QuizOptions>();
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            String query = String.format("SELECT * FROM quizzes WHERE quiz_id = \'%d\' ",quiz_id );
            stmt.executeQuery(query);
            while(resultSet.next()){
                res.add(fetchOption());
            }

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
