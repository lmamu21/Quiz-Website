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

@WebServlet("/singlePageQuiz")
public class singlePageQuizServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //TODO: must be changed

        Quiz quiz = (Quiz) req.getSession().getAttribute("quiz");
        ArrayList<IQuestion> questions = quiz.getQuestions();


        //todo:redirect to the result page and store result
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hola</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        for(int i = 0 ; i < questions.size() ; i ++) {
            out.println("<h1>");
            out.println(req.getParameter("" + (i + 1)));
              out.println( questions.get(i).check(req.getParameter("" + questions.get(i).getIndex())));
            out.println("</h1>");
        }




        out.println("</body>");
        out.println("</html>");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession sess = req.getSession();
        int quiz_id  = Integer.parseInt( (String)sess.getAttribute("quizId"));
        QuizManager manager = (QuizManager) getServletContext().getAttribute("QuizManager");
        Quiz quiz = manager.getQuizForWriting(quiz_id);
        sess.setAttribute("quiz",quiz);

        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/singlePageQuiz/singlePage.jsp");
        dispatcher.forward(req, resp);
    }


}
