package provider;

import framework.URL;
import protocol.Http.HttpServer;
import provider.api.HelloService;
import provider.impl.HelloServiceImpl;
import register.Register;

/**
 * Created by next on 2022/8/23.
 */
public class Provider {
    public static void main(String[]args){
        URL url=new URL("localhost",8080);
        Register.regist(url, HelloService.class.getName(), HelloServiceImpl.class);

        HttpServer server=new HttpServer();
        server.start(url.getHostname(),url.getPort());
    }
}
