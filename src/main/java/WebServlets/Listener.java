package WebServlets;

import Commons.AccountManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.tomcat.jdbc.pool.DataSource;


import org.apache.tomcat.jdbc.pool.PoolProperties;
@WebListener
public class Listener  implements ServletContextListener {

    private final static String DatabaseName = "quiz_test";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        AccountManager manager  = new AccountManager(DatabaseName);
        servletContextEvent.getServletContext().setAttribute("AccountManager",manager);

        PoolProperties properties = new PoolProperties();
        properties.setDriverClassName("name");
        properties.setUrl("jdbc:mysql://localhost:3306/");
        properties.setUsername("root");
        properties.setPassword("root1234");
        properties.setInitialSize(100);


        DataSource pool = new DataSource();
        pool.setPoolProperties(properties);



    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
