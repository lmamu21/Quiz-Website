package WebServlets;

import Commons.*;
import Commons.Interfaces.IQuestion;
import Commons.Questions.FillTheBlankQuestion;
import Commons.Questions.MultipleChoiceQuestion;
import Commons.Questions.PictureResponseQuestion;
import Commons.Questions.QuestionResponseQuestion;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
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

        Quiz quiz = (Quiz) req.getSession().getAttribute("quiz");
        ArrayList<IQuestion> questions = quiz.getQuestions();
        ArrayList<Integer> answersInt = new ArrayList<>();
        BigDecimal totalMark = BigDecimal.ZERO;
        ArrayList<String> userAnswers = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
//            out.println("<h1>");
            String userAnswer = (String) req.getParameter("" + questions.get(i).getIndex());
            System.out.println("UserAnswers: " + userAnswer);
            userAnswers.add(userAnswer);
            int questionMark = questions.get(i).check(req.getParameter("" + questions.get(i).getIndex()));
            BigDecimal questionMarkBigDecimal = new BigDecimal(questionMark);
            totalMark = totalMark.add(questionMarkBigDecimal);
            answersInt.add(questionMark);
        }
        HttpSession sess = req.getSession();
        sess.setAttribute("answers", userAnswers);
        System.out.println(userAnswers.size());
        sess.setAttribute("marksArrayList", answersInt);
        sess.setAttribute("totalMark", totalMark);

        long finishTime = System.currentTimeMillis();

        long startTime = (long) sess.getAttribute("startTime");


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

        sess.setAttribute("elapsedTime", elapsedTimeString);
        AccountManager manager = (AccountManager) sess.getAttribute("AccountManager");
        String username = (String) sess.getAttribute("username");
        int user_id = 0;
        try {
             user_id = Integer.parseInt(manager.getID(username));
        } catch (Exception e) {
            e.printStackTrace();
        }

        LocalDateTime  startTimeTemp = (LocalDateTime) sess.getAttribute("startTimeTemp");
        LocalDateTime nowTmp = LocalDateTime.now();
        Duration duration = Duration.between(startTimeTemp,nowTmp);
        long timeTakenSeconds = duration.getSeconds();
        int quiz_id  = Integer.parseInt( (String)sess.getAttribute("quizId"));

        Timestamp now = Timestamp.valueOf(nowTmp);



        ServletContext sce = getServletContext();
        SummaryPageService summaryService =(SummaryPageService) sce.getAttribute("SummaryPageService");
        sess.setAttribute("timeTakenSeconds",timeTakenSeconds);
        totalMark =(BigDecimal) sess.getAttribute("totalMark");


        QuizAttempt attempt = new QuizAttempt(0,quiz_id,user_id,now,timeTakenSeconds,totalMark);

        try {
            summaryService.saveQuizAttempt(attempt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//
       

        RequestDispatcher dispatcher = req.getRequestDispatcher("result/result.jsp");
        dispatcher.forward(req, res);
    }
    //todo:redirect to the result page and store result
//        res.setContentType("text/html");
//        PrintWriter out = res.getWriter();
//        out.println("<html>");
//        out.println("<head>");
//        out.println("<title>Hola</title>");
//        out.println("</head>");
//        out.println("<body bgcolor=\"white\">");
    //for(int i = 0 ; i < questions.size() ; i ++) {
//            out.println("<h1>");
    // out.println(req.getParameter("" + (i + 1)));
    // out.println( et(i).getIndex())));


//            out.println("</h1>");
//        }




    //  out.println("</body>");
    // out.println("</html>");
    //}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession sess = req.getSession();
        int quiz_id  = Integer.parseInt( (String)sess.getAttribute("quizId"));
        QuizManager manager = (QuizManager) getServletContext().getAttribute("QuizManager");
        Quiz quiz = manager.getQuizForWriting(quiz_id);
        sess.setAttribute("quiz",quiz);
        long startTime = System.currentTimeMillis();
        sess.setAttribute("startTime", startTime);


        RequestDispatcher dispatcher = req.getRequestDispatcher("/singlePageQuiz/singlePage.jsp");
        dispatcher.forward(req, resp);
    }


}