package Commons.Dao;

import Commons.Interfaces.IQuestion;
import Commons.Questions.FillTheBlankQuestion;
import Commons.Questions.MultipleChoiceQuestion;
import Commons.Questions.PictureResponseQuestion;
import Commons.Questions.QuestionResponseQuestion;
import Commons.Quiz;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {

    private Connection con;
    private Statement stmt;
    private ResultSet resultSet;
    private DataSource pool;
    private String databaseName;
    private List<String> tablenames;
    private static String correctAnswerTableName = "correct_answers";

    public QuestionDao(DataSource pool, String databaseName) {
        this.pool = pool;
        this.databaseName = databaseName;
        tablenames = new ArrayList<>();
        tablenames.add(FillTheBlankQuestion.tableName);
        tablenames.add(MultipleChoiceQuestion.tableName);
        tablenames.add(PictureResponseQuestion.tableName);
        tablenames.add(QuestionResponseQuestion.tableName);
    }

    //correct answers are stored in different tables with prefix of question table name and then _correct_answer example : multiple_choice_correct_answers
    public synchronized List<String> getCorrectAnswers(int question_id,String tableName){
        List<String> correctAnswers = new ArrayList<>();
        try {
            con = pool.getConnection();
            String query = "SELECT answer FROM " + tableName + "_" +correctAnswerTableName + " WHERE question_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, question_id);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String answer = resultSet.getString("answer");
                correctAnswers.add(answer);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignored) {
                }
            }
        }
        return correctAnswers;
    }
    public synchronized List<IQuestion> getQuestions(int quiz_id){
        con = null;
        List<IQuestion> questions =  new ArrayList<IQuestion>();
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            for(String tableName: tablenames){
                String query ="SELECT * FROM "+ tableName + "WHERE quiz_id = " + quiz_id;
                stmt.executeQuery(query);
                while(resultSet.next()){
                    switch (tableName){
                        case QuestionResponseQuestion.tableName:
                            questions.add(fetchQuestionResponse());
                            break;
                        case    MultipleChoiceQuestion.tableName:
                            questions.add(fetchPictureResponse());
                            break;
                        case PictureResponseQuestion.tableName:
                            questions.add(fetchMultipleChoice());
                            break;
                        case FillTheBlankQuestion.tableName:
                            questions.add(fetchFillInTheBlank());
                            break;
                    }
                }
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

        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            for(IQuestion question : questions){
                int indx = question.getIndex();
                int quizId= question.getQuizId();
                resultSet = stmt.executeQuery("SELECT id FROM " + question.getTableName() + " WHERE quiz_id  = "  + quizId  + " and question_index = "  + indx);
                if(resultSet.next()){
                    question.setId(resultSet.getInt("id"));
                }
            }
            for(IQuestion question : questions)
                question.setCorrectAnswers(getCorrectAnswers(question.getId(),question.getTableName()));
            for(IQuestion question : questions)
                question.fillAdditionalData(con);

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


        return questions;
    }

    public synchronized void addQuestion(IQuestion question , int quizId){
        question.setQuizId(quizId);
        con = null;
        ArrayList<Quiz> res = new ArrayList<Quiz>();
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            PreparedStatement stmt = question.prepareAddStatement(con);
            stmt.executeQuery();
            stmt = question.prepareAdditionalDataAddStatement(con);
            stmt.executeQuery();
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

    }

    private IQuestion fetchQuestionResponse(){
        QuestionResponseQuestion res = null;
        try {
            int id = resultSet.getInt("id");
            int quizId = resultSet.getInt("quiz_id");
            int index = resultSet.getInt("question_index");
            String question = resultSet.getString("question");
            int mark = resultSet.getInt("mark");
            res = new QuestionResponseQuestion(id, quizId, index, question, mark);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    private IQuestion fetchPictureResponse(){
        IQuestion res = null;
        try {
            int id = resultSet.getInt("id");
            int quizId = resultSet.getInt("quiz_id");
            int index = resultSet.getInt("question_index");
            String imgUrl = resultSet.getString("img_url");
            int mark = resultSet.getInt("mark");
            res = new PictureResponseQuestion(id, quizId, index, imgUrl, mark);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    private IQuestion fetchMultipleChoice(){
        IQuestion res = null;
        try {
            int id = resultSet.getInt("id");
            int quizId = resultSet.getInt("quiz_id");
            int index = resultSet.getInt("question_index");
            String question = resultSet.getString("question");
            int mark = resultSet.getInt("mark");
            res = new MultipleChoiceQuestion(id, quizId, index, question, mark);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    private IQuestion fetchFillInTheBlank(){
        IQuestion res = null;
        try {
            int id = resultSet.getInt("id");
            int quizId = resultSet.getInt("quiz_id");
            int index = resultSet.getInt("question_index");
            String questionHead = resultSet.getString("question_head");
            String questionTail = resultSet.getString("question_tail");
            int mark = resultSet.getInt("mark");
            res = new FillTheBlankQuestion(id, quizId, index, questionHead, questionTail, mark);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }




}
