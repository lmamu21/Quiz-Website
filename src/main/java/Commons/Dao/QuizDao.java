package Commons.Dao;

import Commons.Dao.OptionDao;
import Commons.Dao.QuestionDao;
import Commons.Interfaces.IQuestion;
import Commons.Question;
import Commons.Quiz;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDao {

    private Connection con;
    private Statement stmt;
    private ResultSet resultSet;
    private DataSource pool;
    private String databaseName;
    private QuestionDao questionDao;
    private OptionDao optionDao;
    public QuizDao(DataSource _pool, String _databaseName , QuestionDao _questionDao,OptionDao optionDao) {
        this.pool = _pool;
        this.databaseName = _databaseName;
        this.questionDao = _questionDao;
        this.optionDao = optionDao;
    }

    public synchronized void addQuiz(Quiz quiz){
        ArrayList<IQuestion> questions = quiz.getQuestions();
        con = null;
        ArrayList<Quiz> res = new ArrayList<Quiz>();
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            stmt.executeQuery("USE " + databaseName);

            String query = "INSERT INTO quizzes (quiz_name, quiz_description, creator) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, quiz.getQuizName());
            ps.setString(2, quiz.getQuizDescription());
            ps.setInt(3, quiz.getCreatorID());
            int ID = -1;
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {

                resultSet = ps.getGeneratedKeys();
                ID = resultSet.getInt("quiz_id");
                System.out.println("A new quiz was inserted successfully!");
            }


            if(ID != -1){
                //updating question tables;
                List<IQuestion> qs = quiz.getQuestions();
                for(IQuestion q : qs)
                    questionDao.addQuestion(q,ID);
                //updating options tables
                List<Quiz.QuizOptions> qo = quiz.getQuizOptions();
                for(Quiz.QuizOptions o : qo)
                    optionDao.addOption(o,ID);
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
    }

    public synchronized Quiz getQuizForWriting(int quiz_id){
        con = null;
        Quiz res = null;
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            stmt.executeQuery("USE " + databaseName);
            String query = "SELECT * FROM quizzes WHERE  quiz_id = " + quiz_id;
            resultSet = stmt.executeQuery(query);
            if(resultSet.next()){
                res = fetchQuiz();
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
        if(res == null)
            return null;
        res.setQuizOptions( (ArrayList<Quiz.QuizOptions>) optionDao.getOptions(res.getQuizID()));
        res.setQuestions((ArrayList<IQuestion>) questionDao.getQuestions(res.getQuizID()));
        return res;
    }
    public synchronized Quiz getQuiz(int quiz_id){
        Quiz res  = null;
        con = null;
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            stmt.executeQuery("USE " + databaseName);
            String query = "SELECT * FROM quizzes WHERE quiz_id = " + quiz_id;
            resultSet = stmt.executeQuery(query);
            if(resultSet.next()){
                res  = fetchQuiz();
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
    public synchronized List<Quiz> getQuizzes(){
        con = null;
        ArrayList<Quiz> res = new ArrayList<Quiz>();
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            stmt.executeQuery("USE " + databaseName);
            String query = "SELECT * FROM quizzes ";
            resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                res.add(fetchQuiz());
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
    public synchronized List<Quiz> getRecentQuizzes(int maxCount){
        con = null;
        ArrayList<Quiz> res = new ArrayList<Quiz>();
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            stmt.executeQuery("USE " + databaseName);
            String query = "SELECT * FROM quizzes  ORDER BY created_at DESC";
            resultSet = stmt.executeQuery(query);
            while(resultSet.next() && res.size() < maxCount){
                res.add(fetchQuiz());
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

    public synchronized List<Quiz> getUsersQuizzes(int user_id){
        con = null;
        ArrayList<Quiz> res = new ArrayList<Quiz>();
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            stmt.executeQuery("USE " + databaseName);
            String query = "SELECT * FROM quizzes WHERE creator = " + user_id;
            resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                res.add(fetchQuiz());
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

    private Quiz fetchQuiz(){
        Quiz quiz = null;
        try{
            int quizId = resultSet.getInt("quiz_id");
            String quizName = resultSet.getString("quiz_name");
            String quizDescription = resultSet.getString("quiz_description");
            int creatorId = resultSet.getInt("creator");
            quiz = new Quiz(quizId ,quizName,quizDescription,creatorId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return quiz;
    }


}
