package Commons;

import Commons.Dao.QuizDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizManager {
    private QuizDao dao;

    public QuizManager(QuizDao _dao){
        this.dao = _dao;
    }

    public List<Quiz> getPopularQuizzes(int num){
        ArrayList<Quiz> list = (ArrayList<Quiz>) dao.getQuizzes();
        //todo
       // Collections.sort(list, (a, b) -> Integer.compare(0,0));
        return list;
    }
    public List<Quiz> getRecentQuizzes(int num){
        return dao.getRecentQuizzes(num);
    }

    public List<Quiz> getQuizzes(){
        return dao.getQuizzes();
    }

    public List<Quiz> getUsersQuizzes(int user_id){
        return dao.getUsersQuizzes(user_id);
    }
    public void addQuiz(Quiz quiz){
        dao.addQuiz(quiz);
    }

    public Quiz getQuizForWriting(int quiz_id){
        return dao.getQuizForWriting(quiz_id);
    };


    public Quiz getQuiz(int quizId) {
        //TODO
        return null;
    }
}
