package WebServlets;


import Commons.AccountManager;

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

@WebServlet("/Homepage")
public class HomepageServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession();
        if(sess.getAttribute("username") == null){
            response.sendRedirect("/Quiz-Web/Login");
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage/homepage.jsp");
        dispatcher.forward(request, response);
    }


}