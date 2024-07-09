package WebServlets;

import Commons.*;
import Commons.Announcement.AnnouncementDAO;
import Commons.Announcement.AnnouncementManager;
import Commons.Dao.OptionDao;
import Commons.Dao.QuestionDao;


import Commons.Dao.QuestionDao;
import Commons.Dao.QuizDao;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.tomcat.jdbc.pool.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;

@WebListener
public class Listener  implements ServletContextListener {


    private final static String DatabaseName = "test_db";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext context = servletContextEvent.getServletContext();
        try{
            // Create and set connection pool parameters.
            PoolProperties properties = new PoolProperties();
            properties.setDriverClassName("com.mysql.cj.jdbc.Driver");
            properties.setUrl("jdbc:mysql://localhost:3306/"+DBInfo.MYSQL_DATABASE_NAME);
            properties.setUsername(DBInfo.MYSQL_USERNAME);
            properties.setPassword(DBInfo.MYSQL_PASSWORD);
            properties.setInitialSize(DBInfo.MYSQL_POOL_INITIAL_SIZE);
            DataSource pool = new DataSource();
            pool.setPoolProperties(properties);
            SummaryPageService summaryPageService = new SummaryPageService(new SummaryPageDAO(pool,DBInfo.MYSQL_DATABASE_NAME));
            QuestionDao questionDao = new QuestionDao(pool,DBInfo.MYSQL_DATABASE_NAME);
            OptionDao optionDao = new OptionDao(pool,DBInfo.MYSQL_DATABASE_NAME);
            QuizManager quizManager = new QuizManager(new QuizDao(pool,DBInfo.MYSQL_DATABASE_NAME,questionDao,optionDao));
            AccountManager accountManager  = new AccountManager(new AccountManagerDAO(pool,DBInfo.MYSQL_DATABASE_NAME));
            AnnouncementDAO announcementDAO = new AnnouncementDAO(pool,DBInfo.MYSQL_DATABASE_NAME);
            AnnouncementManager announcementManager = new AnnouncementManager(announcementDAO);
            context.setAttribute("announcementManager", announcementManager);
            context.setAttribute("pool",pool);
            context.setAttribute("SummaryPageService", summaryPageService);
            context.setAttribute("QuizManager",quizManager);
            context.setAttribute("AccountManager",accountManager);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
