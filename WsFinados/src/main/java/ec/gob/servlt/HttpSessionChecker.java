package ec.gob.servlt;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class HttpSessionChecker implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent event) {
        System.out.printf("Session ID %s created at %s%n", event.getSession().getId(), new Date());
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.printf("Session ID %s destroyed at %s%n", event.getSession().getId(), new Date());
    }

}
