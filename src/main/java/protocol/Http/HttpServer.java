package protocol.Http;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * @author next
 * Created by next on 2022/8/23.
 */
public class HttpServer {
    public void start(String hostname,Integer port){
        Tomcat tomcat=new Tomcat();

        Server server=tomcat.getServer();
        Service service=server.findService("Tomcat");

        Connector connector=new Connector();
        connector.setPort(port);

        Engine engine=new StandardEngine();
        engine.setDefaultHost(hostname);

        Host host=new StandardHost();
        host.setName(hostname);

        String contexPath="";
        Context context=new StandardContext();
        context.setPath(contexPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);

        tomcat.addServlet(contexPath,"dispatcher",new DispathServlet());
        context.addServletMappingDecoded("/*","dispatcher");

        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

    }
}
