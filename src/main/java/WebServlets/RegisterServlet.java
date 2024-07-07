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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException{
        res.setContentType("text/html");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat-password");
        AccountManager manager = (AccountManager) getServletContext().getAttribute("AccountManager");

        HttpSession session = req.getSession();
        String registerStatus = "";


        if(!password.equals(repeatPassword)){
            session.setAttribute("registerStatus", "no match");
            res.sendRedirect("/Quiz_Web_war/register");
            return;
        }

        boolean ans = false;
        PrintWriter writer = res.getWriter();

        try {
            ans = manager.registerUser(username,password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        if(ans){
            registerStatus = "loggedIn";
            session.setAttribute("loginStatus",registerStatus);
            session.setAttribute("username", username);
            res.sendRedirect("/Quiz_Web_war/homepage");
        }else {
            registerStatus = "already used";
            session.setAttribute("registerStatus",registerStatus);
            res.sendRedirect("/Quiz_Web_war/register");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        RequestDispatcher dispatcher = req.getRequestDispatcher("/register/register.jsp");
        dispatcher.forward(req, resp);
    }


}
