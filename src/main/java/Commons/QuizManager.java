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
        Collections.sort(list, (a, b) -> Integer.compare(0,0));
        return list.subList(0,Math.min(list.size() - 1 , num - 1));
    }
    public List<Quiz> getRecentQuizzes(int num){
        ArrayList<Quiz> list = (ArrayList<Quiz>) dao.getQuizzes();
        //todo
        Collections.sort(list, (a, b) -> Integer.compare(0 ,0));
        return list.subList(0,Math.min(list.size() - 1 , num - 1));
    }

    public List<Quiz> getUsersQuizzes(int user_id){
        return dao.getUsersQuizzes(user_id);
    }
    public void addQuiz(Quiz quiz){
        dao.addQuiz(quiz);
    }


    public Quiz getQuiz(int quizId) {
        //TODO
        return null;
    }
}
