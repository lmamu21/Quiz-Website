package WebServlets;

import Commons.AccountManager;
import Commons.DBInfo;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

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

    private final static String DatabaseName = "test_db";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext context = servletContextEvent.getServletContext();
        try{
            // Create and set connection pool parameters.
            PoolProperties properties = new PoolProperties();
            properties.setDriverClassName("DBInfo");
            properties.setUrl(DBInfo.MYSQL_URL);
            properties.setUsername(DBInfo.MYSQL_USERNAME);
            properties.setPassword(DBInfo.MYSQL_PASSWORD);
            properties.setInitialSize(DBInfo.MYSQL_POOL_INITIAL_SIZE);
            DataSource pool = new DataSource();
            pool.setPoolProperties(properties);
            SummaryPageService summaryPageService = new SummaryPageService(new SummaryPageDAO(pool,DBInfo.MYSQL_DATABASE_NAME));


            context.setAttribute("pool",pool);
            context.setAttribute("SummaryPageService", summaryPageService);




        }catch (Exception ignored){

        }

        AccountManager manager  = new AccountManager(DatabaseName);
        servletContextEvent.getServletContext().setAttribute("AccountManager",manager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
