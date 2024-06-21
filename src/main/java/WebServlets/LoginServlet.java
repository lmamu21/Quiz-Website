package WebServlets;
import Commons.AccountManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AccountManager manager = (AccountManager) getServletContext().getAttribute("AccountManager");

        boolean ans = false;
        String loginStatus = "";

        HttpSession session = req.getSession();
        PrintWriter writer = res.getWriter();

        try {
            ans = manager.authenticateUser(username,password);
            if(ans) {
                loginStatus = "loggedIn";
                session.setAttribute("loginStatus",loginStatus);
                session.setAttribute("username",username);
            }else {
                loginStatus = "wrong password";
                session.setAttribute("loginStatus",loginStatus);
            }
        } catch (IllegalArgumentException e) {
            loginStatus = "user not found";
            session.setAttribute("loginStatus",loginStatus);
        } catch (Exception e){
            e.printStackTrace();
        }



        res.sendRedirect("/Quiz-Web/Login");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession sess = req.getSession();
        if(sess.getAttribute("loginStatus") != null && ((String)sess.getAttribute("loginStatus")).equals("loggedIn") ){
            res.sendRedirect("/Quiz-Web/Homepage");
            return;
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/login/index.jsp");
        dispatcher.forward(req, res);
    }

}