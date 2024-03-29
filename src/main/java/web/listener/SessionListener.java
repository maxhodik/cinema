package web.listener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);
    private static int count = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
            count++;
        LOGGER.info("Add new session count = " + count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        if (--count <= 0) {
            count = 0;
        }
        LOGGER.info("Remove session, current count = " + count);
    }


}
