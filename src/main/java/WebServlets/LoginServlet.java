package WebServlets;


import Commons.AccountManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AccountManager manager = (AccountManager) getServletContext().getAttribute("AccountManager");

        boolean ans = false;
        PrintWriter writer = resp.getWriter();

        try {
            ans = manager.authenticateUser(username,password);
        } catch (Exception e) {
            writer.write("<h1>user not found!!!!!!!!</h1>");
            //user not found;
            throw new RuntimeException(e);
        }


        if(ans) {
            writer.write("<h1>hello logged in successfully</h1>");
            // user logged in successfully
            //TODO:writeResponse;
        }else {
            writer.write("<h1>wrong password</h1>");
            // wrong password;
            //TODO : writeResponse;
        }
    }

}