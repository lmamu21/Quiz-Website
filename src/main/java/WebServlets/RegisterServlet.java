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
            throw new RuntimeException(e);
        }
        HttpSession session = req.getSession();
        String registerStatus = "";
        if(ans){
            registerStatus = "registered";
            session.setAttribute("registerStatus",registerStatus);
            res.sendRedirect("/Quiz-Web/Homepage");
        }else {
            registerStatus = "username already exists";
            session.setAttribute("registerStatus",registerStatus);
            res.sendRedirect("/Quiz-Web/Register");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/Register/register.jsp");
        dispatcher.forward(req, res);
    }


}
