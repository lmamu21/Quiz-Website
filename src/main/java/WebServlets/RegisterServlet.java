package WebServlets;


import Commons.AccountManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException{
        res.setContentType("text/html");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AccountManager manager = (AccountManager) getServletContext().getAttribute("AccountManager");

        boolean ans = false;
        PrintWriter writer = res.getWriter();

        try {
            ans = manager.registerUser(username,password);
        } catch (NoSuchAlgorithmException e) {
            // could not hash the string returning no such algorithm exception
            throw new RuntimeException(e);
        }

        if(ans){
            writer.write("<h1>hello registered  successfully</h1>");
            // registered user successfully;
        }else {
            writer.write("<h1>username already exists  :((((((((( </h1>");
            // username already exists;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Register/register.jsp");
        dispatcher.forward(request, response);
    }


}
