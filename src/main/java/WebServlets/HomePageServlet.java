package WebServlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import Commons.User;

public class HomePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        HttpSession session = httpServletRequest.getSession();
        if(session == null || session.getAttribute("User")==null){
            httpServletResponse.sendRedirect("login.jsp");
        }
        User user = (User)session.getAttribute("User");

        List<Announcement> announcement = AnnouncmentDAO.getRecentAnnouncements();
        List<Quiz> popularQuizzes = QuizDAO.getPopularQuizzes();
       // List<QuizActivity> userQuizActivity = QuizActivityDAO.getRecentActivitiesByUser(user.getUsername());
       // List<Quiz> userCreatedQuizzes = QuizDao.getQuizzesCreatedByUser(user.getUsername());

        //List<Achievement> achievements = AchievementDAO.getAchievementsByUser(user.getUsername());
        //List<Message> recentMessages = MessageDAO.getRecentMessagesByUser(user.getUsername());
        //List<Activity> friendsActivities = ActivityDAO.getFriendsRecentActivities(user.getUsername());

        request.setAttribute("announcements", announcements);
        request.setAttribute("popularQuizzes", popularQuizzes);
        request.setAttribute("recentQuizzes", recentQuizzes);
        //request.setAttribute("userQuizActivities", userQuizActivities);
        //request.setAttribute("userCreatedQuizzes", userCreatedQuizzes);
        //request.setAttribute("achievements", achievements);
        //request.setAttribute("recentMessages", recentMessages);
        //request.setAttribute("friendsActivities", friendsActivities);

        // Forward to home.jsp
        request.getRequestDispatcher("home.jsp").forward(request, response);



    }

    @Override
    protected void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        super.doPut(httpServletRequest, httpServletResponse);
    }
}
