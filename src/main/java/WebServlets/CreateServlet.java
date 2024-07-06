package WebServlets;

import Commons.Answer;
import Commons.Question;
import Commons.Quiz;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/create/create.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        for(String s : request.getParameterMap().keySet()){
            String value = request.getParameter(s);
            if(value != null){
                System.out.println(s+": "+value);
            }
            else{
                System.out.println(s+": ");
            }
        }

        String username = request.getParameter("username");
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String quizName = request.getParameter("quiz-name");
        String quizDescription = request.getParameter("quiz-description");
        Quiz.QuizOptions random = request.getParameter("random") != null ? Quiz.QuizOptions.RANDOM_QUESTIONS : null;
        Quiz.QuizOptions immediate = request.getParameter("immediate") != null ? Quiz.QuizOptions.IMMEDIATE_CORRECTION : null;
        Quiz.QuizOptions page_option = request.getParameter("page-option ").equals("one-page") ? Quiz.QuizOptions.ONE_PAGE : Quiz.QuizOptions.MULTIPLE_PAGES;

        ArrayList<Quiz.QuizOptions> options = new ArrayList<>();
        if(random != null){
            options.add(random);
        }
        if(immediate != null){
            options.add(immediate);
        }
        options.add(page_option);

        ArrayList<Question> questions = new ArrayList<>();
        int i = 1;
        while(true){
            String typeString = request.getParameter("question-"+i+"-type");
            if(typeString == null){
                break;
            }

            Question.QuestionType type = null;
            if(typeString.equals("multiple-choice")){
                type= Question.QuestionType.MULTIPLE_CHOICE;
            }else if(typeString.equals("image-response")){
                type=Question.QuestionType.PICTURE_RESPONSE;
            }else if(typeString.equals("question-response")){
                type=Question.QuestionType.QUESTION_RESPONSE;
            }else if(typeString.equals("fill-in-the-blank")){
                type=Question.QuestionType.FILL_IN_THE_BLANK;
            }
            String questionString;
            if(type==Question.QuestionType.FILL_IN_THE_BLANK){
               questionString = request.getParameter("question-"+i+"-before") + '\\'+request.getParameter("question-"+i+"-after");
            }else{
                questionString = request.getParameter("question-"+i);
           }

            int questionIndex = i;

            String url = null;
            if(type==Question.QuestionType.PICTURE_RESPONSE){
                url = request.getParameter("question-"+i+"-image-url");
            }

            ArrayList<Answer> answers = new ArrayList<>();

            int j = 1;
            while(true){
                String answerString = request.getParameter("answer-"+i+"-answer-"+j);
                if(answerString == null){
                    break;
                }

            }

            int mark = 1; // to be added to client-side to choose and then it will be changed here with getParameter

            Question question = new Question(0, type, questionString, questionIndex, url, answers, mark);

            questions.add(question);

        }

        //Quiz quiz = new Quiz(0, options, quizName, quizDescription, 0 /*not correct*/ , questions);
        //todo to be added to database using dao. (not currently on this branch)

        RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage/homepage.jsp");
        dispatcher.forward(request, response);
    }
}
