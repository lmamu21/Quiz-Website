package WebServlets;

import Commons.AccountManager;
import Commons.DBInfo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import Commons.SummaryPageDAO;
import Commons.SummaryPageService;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;


@WebListener
public class Listener  implements ServletContextListener {

    private final static String DatabaseName = "quiz";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext context = servletContextEvent.getServletContext();
        try{
            // Create and set connection pool parameters.
            PoolProperties properties = new PoolProperties();
            properties.setDriverClassName(DBInfo;
            properties.setUrl(DBInfo.MYSQL_URL);
            properties.setUsername(DBInfo.MYSQL_USERNAME);
            properties.setPassword(DBInfo.MYSQL_PASSWORD);
            properties.setInitialSize(DBInfo.MYSQL_POOL_INITIAL_SIZE);
            DataSource pool = new DataSource();
            pool.setPoolProperties(properties);
            SummaryPageService summaryPageService = new SummaryPageDAO(new SummaryPageDAO(pool,DBInfo.MYSQL_DATABASE_NAME));


            context.setAttribute(ContextKey.CONNECTION_POOL, pool);
            context.setAttribute(ContextKey.SummaryPageService, summaryPageService);



        }catch (Exception ignored){

        }

        AccountManager manager  = new AccountManager(DatabaseName);
        servletContextEvent.getServletContext().setAttribute("AccountManager",manager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
