
package WebServlets;

import Commons.Dao.QuizDao;
import Commons.Interfaces.IQuestion;
import Commons.Quiz;
import Commons.QuizManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@WebServlet("/takeQuiz")
public class TakeQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quiz_id = Integer.parseInt(request.getParameter("quiz_id")); //hidden input on summary page.

        QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quizManager"); //todo not real identifier

        HttpSession session = request.getSession();
        session.setAttribute("quiz", quizManager.getQuiz(quiz_id));

        Quiz quiz = quizManager.getQuiz(quiz_id);
        ArrayList<Quiz.QuizOptions> options = quiz.getQuizOptions();

        ArrayList<IQuestion> questions = quiz.getQuestions();

        if(options.contains(Quiz.QuizOptions.RANDOM_QUESTIONS)){
            Collections.shuffle(questions);
        }

        if(options.contains(Quiz.QuizOptions.IMMEDIATE_CORRECTION)){
            session.setAttribute("immediate", true);
        }

        quiz.setQuestions(questions);

        long startTime = System.currentTimeMillis();
        session.setAttribute("startTime", startTime);

        if(options.contains(Quiz.QuizOptions.ONE_PAGE)){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("//");
            requestDispatcher.forward(request, response);
        }else{
            session.setAttribute("currentIndex", null);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/multiplePageQuiz");
            requestDispatcher.forward(request, response);
        }

    }
}
