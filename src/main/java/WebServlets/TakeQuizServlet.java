
package WebServlets;

import Commons.Dao.QuizDao;
import Commons.Quiz;
import Commons.QuizManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpContext;
import java.io.IOException;
import java.util.ArrayList;

public class TakeQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));

         QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quizManager"); //todo not real identifier

        Quiz quiz = quizManager.getQuiz(quiz_id);
        ArrayList<Quiz.QuizOptions> options = quiz.getQuizOptions();
        if(options.contains(Quiz.QuizOptions.ONE_PAGE)){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("//");
            requestDispatcher.forward(request, response);
        }else{
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("//");
            requestDispatcher.forward(request, response);
        }

    }
}
