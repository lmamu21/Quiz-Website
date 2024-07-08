package WebServlets;


import Commons.AccountManager;
import Commons.Dao.QuizDao;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/homepage")
public class HomepageServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession();
        ServletContext sce = getServletContext();
        QuizManager quizManager = (QuizManager) sce.getAttribute("QuizManager");

        ArrayList<Quiz> recents =(ArrayList<Quiz>) quizManager.getRecentQuizzes(5);
        sess.setAttribute("recentQuizzes" , recents);
        System.out.println(recents.size());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage/homepage.jsp");
        dispatcher.forward(request, response);

    }


}