package WebServlets;

import Commons.Interfaces.IQuestion;
import Commons.Questions.*;
import Commons.Quiz;
import Commons.QuizManager;

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
        Quiz.QuizOptions page_option = request.getParameter("page-option").equals("one-page") ? Quiz.QuizOptions.ONE_PAGE : Quiz.QuizOptions.MULTIPLE_PAGES;

        ArrayList<Quiz.QuizOptions> options = new ArrayList<>();
        if(random != null){
            options.add(random);
        }
        if(immediate != null){
            options.add(immediate);
        }
        options.add(page_option);

        ArrayList<IQuestion> questions = new ArrayList<>();
        int i = 1;
        while(true){
            String typeString = request.getParameter("question-"+i+"-type");
            if(typeString == null){
                break;
            }

            IQuestion question = null;
            if(typeString.equals("multiple-choice")){
                String questionString = request.getParameter("question-"+i);
                ArrayList<String> answers = new ArrayList<>();
                ArrayList<String> correctAnswers = new ArrayList<>();

                int j = 0;
                while(true){
                    String choiceString = request.getParameter("question-"+i+"-choice-"+j);
                    if(choiceString == null){
                        break;
                    }

                    answers.add(choiceString);

                    if(request.getParameter("question-"+i+"-choice-"+j+"-isCorrect") != null){
                        correctAnswers.add(choiceString);
                    }

                    j++;
                }
                int mark = Integer.parseInt(request.getParameter("question-"+i+"-mark"));
                question = new MultipleChoiceQuestion(i, questionString, answers, correctAnswers, mark);
                questions.add(question);
            }else if(typeString.equals("image-response")){
                String questionString = request.getParameter("question-"+i);
                String url = request.getParameter("question-"+i+"-url");
                ArrayList<String> answers = new ArrayList<>();
                ArrayList<String> correctAnswers = new ArrayList<>();

                int j = 0;
                while(true){
                    String choiceString = request.getParameter("question-"+i+"-choice-"+j);
                    if(choiceString == null){
                        break;
                    }

                    answers.add(choiceString);

                    if(request.getParameter("question-"+i+"-choice-"+j+"-isCorrect") != null){
                        correctAnswers.add(choiceString);
                    }

                    j++;
                }
                int mark = Integer.parseInt(request.getParameter("question-"+i+"-mark"));
                question = new PictureResponseQuestion(i, url, correctAnswers, mark);
                questions.add(question);
            }else if(typeString.equals("question-response")){
                String questionString = request.getParameter("question-"+i);
                ArrayList<String> answers = new ArrayList<>();
                ArrayList<String> correctAnswers = new ArrayList<>();

                int j = 0;
                while(true){
                    String choiceString = request.getParameter("question-"+i+"-choice-"+j);
                    if(choiceString == null){
                        break;
                    }

                    answers.add(choiceString);

                    if(request.getParameter("question-"+i+"-choice-"+j+"-isCorrect") != null){
                        correctAnswers.add(choiceString);
                    }

                    j++;
                }
                int mark = Integer.parseInt(request.getParameter("question-"+i+"-mark"));
                question = new QuestionResponseQuestion(i, questionString, correctAnswers, mark);
                questions.add(question);
            }else if(typeString.equals("fill-in-the-blank")){
                String before = request.getParameter("question-"+i+"-before");
                String after = request.getParameter("question-"+i+"-after");
                ArrayList<String> answers = new ArrayList<>();
                ArrayList<String> correctAnswers = new ArrayList<>();

                int j = 0;
                while(true){
                    String choiceString = request.getParameter("question-"+i+"-choice-"+j);
                    if(choiceString == null){
                        break;
                    }

                    answers.add(choiceString);

                    if(request.getParameter("question-"+i+"-choice-"+j+"-isCorrect") != null){
                        correctAnswers.add(choiceString);
                    }

                    j++;
                }
                int mark = Integer.parseInt(request.getParameter("question-"+i+"-mark"));
                question = new FillTheBlankQuestion(i, before , after , correctAnswers, mark);
                questions.add(question);
            }
            i++;
        }
        Quiz quiz = new Quiz(0, options, quizName, quizDescription, user_id, questions);
        QuizManager quizManager = (QuizManager)getServletContext().getAttribute("quizManager");
        quizManager.addQuiz(quiz);


        RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage/homepage.jsp");
        dispatcher.forward(request, response);
    }
}
