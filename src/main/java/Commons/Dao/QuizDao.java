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
    //public Quiz(int quizID, ArrayList<QuizOptions> quizOptions, String quizName, String quizDescription, User creator,
    //                ArrayList<Question> questions) {
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
    public synchronized boolean addQuiz(Quiz quiz){
        con = null;

        String insertSQL = "INSERT INTO quizzes (quiz_name, quiz_description, author_id) VALUES (?,?,?,?)";
        int quiz_id = 0;
        try{
            con = pool.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setString(2, quiz.getQuizDescription());
            preparedStatement.setInt(3, quiz.getCreator());


            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                    if(rs.next()) {
                        quiz_id = rs.getInt(1);
                    }
                }
            }
            else {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        ArrayList<Quiz.QuizOptions> optionsArrayList = quiz.getQuizOptions();
        ArrayList<Question> questionArrayList = quiz.getQuestions();
        for(int i = 0 ; i <optionsArrayList.size() ; i++ )
            optionDao.addOption(optionsArrayList.get(i),quiz_id);
        for(int i = 0 ; i < questionArrayList.size() ; i++)
            questionDao.addQuestion((IQuestion) questionArrayList.get(i),quiz_id);
        return true;
    }
    public synchronized void removeQuiz(){

    }
    public synchronized List<Quiz> getUsersQuizzes(int user_id){
        return null;
    }
    public synchronized List<Quiz> getQuizzes(){
        con = null;
        ArrayList<Quiz> res = new ArrayList<Quiz>();
        try {
            con = pool.getConnection();
            System.out.println("here");
            stmt = con.createStatement();
            String query = "SELECT * FROM quizzes ";
            resultSet=stmt.executeQuery(query);
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




}
