package WebServlets;

import Commons.*;
import Commons.Interfaces.IQuestion;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


@WebServlet("/multiplePageQuiz")
public class multiplePageQuizServlet extends HttpServlet {

    private ArrayList<IQuestion> questions;


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        for(String s : req.getParameterMap().keySet()){
            String value = req.getParameter(s);
            if(value != null){
                System.out.println(s+": "+value);
            }
            else{
                System.out.println(s+": ");
            }
        }
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        questions = quiz.getQuestions();

        if(currentIndex == null){
            ArrayList<Quiz.QuizOptions> options = quiz.getQuizOptions();
            if(options.contains(Quiz.QuizOptions.IMMEDIATE_CORRECTION)){
                session.setAttribute("immediate", true);
            }
            System.out.println("questions size: "+questions.size());
            ArrayList<Boolean> answered = new ArrayList<>(questions.size()); //initialized false by default
            ArrayList<Integer> marks = new ArrayList<>(questions.size());
            ArrayList<String> answers = new ArrayList<>(questions.size());

            for(int i = 0; i<questions.size(); i++) {
                answered.add(false);
            }
            for(int i =0 ;i<questions.size();i++){
                answers.add(null);
            }
            for(int i = 0; i<questions.size();i++){
                marks.add(0);
            }

            session.setAttribute("marks", marks);
            session.setAttribute("currentIndex", 0);
            session.setAttribute("answered", answered);
            session.setAttribute("answers", answers);
            session.setAttribute("showingMark", false);
            RequestDispatcher dispatcher = req.getRequestDispatcher("multiPageQuiz/question.jsp");
            dispatcher.forward(req, resp);
        }

        else{
            currentIndex = currentIndex + 1;
            session.setAttribute("currentIndex", currentIndex);
            RequestDispatcher dispatcher = req.getRequestDispatcher("multiPageQuiz/question.jsp");
            dispatcher.forward(req, resp);
        }

    }



    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession();
        for(String s : req.getParameterMap().keySet()){
            String value = req.getParameter(s);
            if(value != null){
                System.out.println(s+": "+value);
            }
            else{
                System.out.println(s+": ");
            }
        }
        Integer currentIndex = (Integer)session.getAttribute("currentIndex");
        ArrayList<String>  responses = (ArrayList<String>) session.getAttribute("responses");

        if(req.getParameter("finish") == null){
            // user wants to take next question
            if(session.getAttribute("immediate")!=null && (Boolean)session.getAttribute("immediate")){
                if(req.getParameter("submit") != null){
                    //show current score and then change button to next
                    ArrayList<Boolean> answered = (ArrayList<Boolean>) session.getAttribute("answered");
                    answered.set(currentIndex, true);
                    session.setAttribute("answered", answered);
                    session.setAttribute("showingMark", true);

                    IQuestion currentQuestion = questions.get(currentIndex);
                    int questionIndexInQuiz = currentQuestion.getIndex();

                    String userResponse = req.getParameter(String.valueOf(questionIndexInQuiz));
                    ArrayList<String> answers = (ArrayList<String>) session.getAttribute("answers");
                    answers.set(currentIndex, userResponse);
                    session.setAttribute("answers", answers);
                    int mark = currentQuestion.check(userResponse);

                    session.setAttribute("mark", mark);

                    RequestDispatcher dispatcher = req.getRequestDispatcher("multiPageQuiz/question.jsp");
                    dispatcher.forward(req, resp);
                }else if(req.getParameter("next")!=null){
                    session.setAttribute("showingMark", false);
                    doGet(req, resp);
                }

            }else{
                ArrayList<Boolean> answered = (ArrayList<Boolean>) session.getAttribute("answered");
                answered.set(currentIndex, true);

                doGet(req, resp);
            }

        }else if(req.getParameter("finish")!=null){
            long finishTime = System.currentTimeMillis();
            long startTime = (long) session.getAttribute("startTime");
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

            session.setAttribute("elapsedTime", elapsedTimeString);

            RequestDispatcher dispatcher = req.getRequestDispatcher("result/result.jsp");
            dispatcher.forward(req, resp);


        }

        if(currentIndex!= null && responses!=null){
            String response = req.getParameter("response");
            responses.add(response);
            currentIndex++;
            session.setAttribute("currentIndex",currentIndex);
            session.setAttribute("responses", responses);
        }
    }


}
