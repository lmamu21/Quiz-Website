package WebServlets;


import Commons.AccountManager;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@WebServlet("/homepage")
public class HomepageServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession();
        ServletContext sce = getServletContext();
        QuizManager quizManager = (QuizManager) sce.getAttribute("QuizManager");
        List<Quiz> popQuizList = quizManager.getPopularQuizzes(3);
        System.out.println("pop quiz list size: "+popQuizList.size());
        for(int i=0;i<popQuizList.size();i++){
            System.out.println(popQuizList.get(i));
        }
        if(popQuizList.size()==0){
            System.out.println("top Quiz List Size is 0");
        }
        request.setAttribute("PopularQuizList",popQuizList);
        if(sess.getAttribute("username") == null){
            response.sendRedirect("/Quiz_Web_war/register/register.jsp");
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage/homepage.jsp");
        dispatcher.forward(request, response);

    }


}