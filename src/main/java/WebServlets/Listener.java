package WebServlets;

import Commons.AccountManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Listener  implements ServletContextListener {

    private final static String DatabaseName = "quiz_test";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AccountManager manager  = new AccountManager(DatabaseName);
        servletContextEvent.getServletContext().setAttribute("AccountManager",manager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
