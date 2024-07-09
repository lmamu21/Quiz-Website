package WebServlets;

import Commons.AccountManager;
import Commons.Interfaces.IQuestion;
import Commons.Questions.FillTheBlankQuestion;
import Commons.Questions.MultipleChoiceQuestion;
import Commons.Questions.PictureResponseQuestion;
import Commons.Questions.QuestionResponseQuestion;
import Commons.Quiz;
import Commons.QuizManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@WebServlet("/singlePageQuiz")
public class singlePageQuizServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //TODO: must be changed

        long finishTime = System.currentTimeMillis();
        req.getSession().setAttribute("finishTime", finishTime);
        long startTime = (long) req.getSession().getAttribute("startTime");
        long elapsedTime = finishTime - startTime;

        long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) -
                TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
                TimeUnit.HOURS.toSeconds(hours) -
                TimeUnit.MINUTES.toSeconds(minutes);
        long milliseconds = elapsedTime -
                TimeUnit.HOURS.toMillis(hours) -
                TimeUnit.MINUTES.toMillis(minutes) -
                TimeUnit.SECONDS.toMillis(seconds);

        // Format the elapsed time into a string
        String elapsedTimeString = String.format(
                "%02d: %02d:%02d.%03d",
                hours, minutes, seconds, milliseconds);

        req.getSession().setAttribute("elapsedTime", elapsedTimeString);

        Quiz quiz = (Quiz) req.getSession().getAttribute("quiz");
        ArrayList<IQuestion> questions = quiz.getQuestions();
        ArrayList<String> answers = (ArrayList<String>) req.getSession().getAttribute("answers");

        //todo:redirect to the result page and store result
        res.setContentType("text/html");

        for(int i = 0 ; i < questions.size() ; i ++) {
            String response =  req.getParameter(""+(i+1));
            answers.set(i, response);
        }

        req.getSession().setAttribute("answers", answers);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("result/result.jsp");
        requestDispatcher.forward(req, res);

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession sess = req.getSession();
        int quiz_id  = Integer.parseInt( (String)sess.getAttribute("quizId"));
        QuizManager manager = (QuizManager) getServletContext().getAttribute("QuizManager");
        Quiz quiz = manager.getQuizForWriting(quiz_id);
        sess.setAttribute("quiz",quiz);
        long startTime = System.currentTimeMillis();
        sess.setAttribute("startTime", startTime);
        ArrayList<String> answers = new ArrayList<>();
        for(int i = 0 ; i<quiz.getQuestions().size(); i++ ){
            answers.add(null);
        }

        sess.setAttribute("answers", answers);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/singlePageQuiz/singlePage.jsp");
        dispatcher.forward(req, resp);
    }


}
