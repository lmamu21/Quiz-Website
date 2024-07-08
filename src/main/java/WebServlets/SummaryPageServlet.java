package WebServlets;

import Commons.*;

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
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;

@WebServlet("/SummaryPage")
public class SummaryPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, RuntimeException{
        ServletContext servletContext = getServletContext();

// Retrieve attribute using the context key
        SummaryPageService summaryPageService = (SummaryPageService) servletContext.getAttribute("SummaryPageService");
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        String quizIdParam = req.getParameter("quizId");
        if (quizIdParam == null || quizIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing quizId parameter");
            return;
        }

        int quizId;
        try {
            quizId = Integer.parseInt(quizIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quizId parameter");
            return;
        }

        try {
            String quizDescription = summaryPageService.getQuizDescription(quizId);
            String creatorUserId = summaryPageService.getCreatorInfo(quizIdParam);
            String creatorUsername = summaryPageService.getUsername(Integer.parseInt(creatorUserId));
            List<QuizAttempt> recentAttempts = summaryPageService.getRecentTestTakersPerformance(quizId);
            List<QuizAttempt> topPerformers = summaryPageService.getTopPerformersDaily(quizId);
            SummaryStatistics summaryStats = summaryPageService.getSummaryStatistics(quizId);
            req.setAttribute("quizId", quizId);
            req.setAttribute("quizDescription", quizDescription);
            req.setAttribute("creatorUserId", creatorUserId);

            req.setAttribute("creatorUsername", creatorUsername);
            req.setAttribute("recentAttempts", recentAttempts);
            req.setAttribute("topPerformers", topPerformers);
            req.setAttribute("summaryStats", summaryStats);

            // Forward to JSP for rendering
            RequestDispatcher dispatcher = req.getRequestDispatcher("/quizSummary.jsp");
            dispatcher.forward(req, resp);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }


}


