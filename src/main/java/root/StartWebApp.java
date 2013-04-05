package root;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
 
public class StartWebApp {
    public static void main(String[] args) throws Exception {
        String portStr = System.getenv("PORT");
        int port = (portStr == null) ? 8085 : Integer.parseInt(portStr);
        Server server = new Server(port);
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        // change the name of the war as needed.
        webapp.setWar("target/blog-1.0.war");
        server.setHandler(webapp);
 
        server.start();
        server.join();
    }
 
 
}
