package WebServlets;

import Commons.AccountManager;
import Commons.Interfaces.IQuestion;
import Commons.Questions.FillTheBlankQuestion;
import Commons.Questions.MultipleChoiceQuestion;
import Commons.Questions.PictureResponseQuestion;
import Commons.Questions.QuestionResponseQuestion;
import Commons.Quiz;

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

@WebServlet("/singlePageQuiz")
public class singlePageQuizServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //TODO: must be changed

        ArrayList<IQuestion> questions = new ArrayList<IQuestion>();
        ArrayList<String> answers1 = new ArrayList<String>();
        answers1.add("1");
        questions.add(new FillTheBlankQuestion(1,"1 + " , " = 2" , answers1, 1));
        ArrayList<String> answers2 = new ArrayList<>();
        answers2.add("2");
        ArrayList<String> choices = new ArrayList<>();
        choices.add("1");
        choices.add("2");
        choices.add("3");
        questions.add(new MultipleChoiceQuestion(2,"1 + 1 = " , choices,answers2,1));
        ArrayList<String> answers3 = new ArrayList<>();
        answers3.add("3");
        questions.add(new PictureResponseQuestion(3 , "https://upload.wikimedia.org/wikipedia/commons/6/62/Eo_circle_red_number-3.svg" , answers3 , 1 ));
        questions.add(new QuestionResponseQuestion(4,"what is 2 + 2 " , Arrays.asList(new String[]{"4"}), 1));

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
          out.println( questions.get(i).check(req.getParameter("" + (i + 1))));
            out.println("</h1>");
        }




        out.println("</body>");
        out.println("</html>");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession sess = req.getSession();
        int quiz_id  = (Integer) sess.getAttribute("quiz_id");
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/singlePageQuiz/singlePage.jsp");
        dispatcher.forward(req, resp);
    }


}
