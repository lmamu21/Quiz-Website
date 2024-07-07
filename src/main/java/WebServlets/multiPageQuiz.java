package WebServlets;

import Commons.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;



@WebServlet("/onePageQuiz")
public class onePageSevlet extends HttpServlet {

    private ArrayList<Question> questions;

    @Override
    public void init() throws ServletException {
        questions = new ArrayList<>();

        Question q1 = new Question(1, Question.QuestionType.QUESTION_RESPONSE, "What's 2+2", 1, null, null, 5);
        questions.add(q1);
        Question q2 = new Question(2, Question.QuestionType.FILL_IN_THE_BLANK, "Capital of Georgia is _______", 2, null, null, 5);
        questions.add(q2);

        ArrayList<Answer> answers = new ArrayList<>();
        Answer a1 = new Answer(1, "messi", false);
        Answer a2 = new Answer(2, "ronaldo", true);
        Answer a3 = new Answer(3, "lebron", false);
        Answer a4 = new Answer(4, "jordan", false);
        answers.add(a1);
        answers.add(a2);
        answers.add(a3);
        answers.add(a4);
        Question q3 = new Question(3, Question.QuestionType.MULTIPLE_CHOICE, "Who is the most popular athlete?", 3, null, answers, 5);
        questions.add(q3);

        Question q4 = new Question(4, Question.QuestionType.PICTURE_RESPONSE, "What is this picture of?", 4, null, null, 5);
        questions.add(q4);
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer currentIndex = (Integer)session.getAttribute("currentIndex");

        if(currentIndex==0 || currentIndex==null){
            currentIndex=0;
            session.setAttribute("responses",new ArrayList<String>());
        }

        if(currentIndex<questions.size()){
            session.setAttribute("question",questions.get(currentIndex));
            RequestDispatcher rd = req.getRequestDispatcher("/multiPageQuiz/question.jsp");
            rd.forward(req,resp);
        }else{
            RequestDispatcher rd = req.getRequestDispatcher("/multiPageQuiz/summary.jsp");
            rd.forward(req,resp);
        }



//        ArrayList<Question> questions = new ArrayList<>();
//
//        Question q1 = new Question(1, Question.QuestionType.QUESTION_RESPONSE,"Whats 2+2",1,null,null,5);
//        questions.add(q1);
//        Question q2 = new Question(2,Question.QuestionType.FILL_IN_THE_BLANK,"Capital of Georgia is _______",2,null,null,5);
//        questions.add(q2);
//
//        ArrayList<Answer> answers = new ArrayList<>();
//        Answer a1 = new Answer(1,"messi",false);
//        Answer a2 = new Answer(2,"ronaldo",true);
//        Answer a3 = new Answer(3,"lebron",false);
//        Answer a4 = new Answer(4,"jordan",false);
//        answers.add(a1);
//        answers.add(a2);
//        answers.add(a3);
//        answers.add(a4);
//        Question q3 = new Question(3, Question.QuestionType.MULTIPLE_CHOICE,"Who is most popular athlete",3,null,answers,5);
//        questions.add(q3);
//
//        Question q4= new Question(4, Question.QuestionType.PICTURE_RESPONSE,"What is this picture of",4,null,null,5);
//        questions.add(q4);
//
//        ArrayList<Quiz.QuizOptions> mockOptions = new ArrayList<Quiz.QuizOptions>();
//        User user = new User("Andria","Robakidze");
//        req.setAttribute("quiz", new Quiz(1, mockOptions,"testQuiz","testDescription",user,questions));
//        req.setAttribute("questions", questions);
//
//
//
//        RequestDispatcher rd = req.getRequestDispatcher("/onePageQuiz/onePageQuiz.jsp");
//        rd.forward(req,resp);
//    }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession();
        Integer currentIndex = (Integer)session.getAttribute("currentIndex");
        ArrayList<String>  responses = (ArrayList<String>) session.getAttribute("responses");
        if(currentIndex!= null && responses!=null){
            String response = req.getParameter("response");
            responses.add(response);
            currentIndex++;
            session.setAttribute("currentIndex",currentIndex);
            session.setAttribute("responses", responses);
        }
    }

}
