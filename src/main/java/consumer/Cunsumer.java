package consumer;

import framework.Invocation;
import framework.ProxyFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import protocol.Http.HttpCient;
import protocol.Http.HttpServer;
import provider.api.HelloService;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author next
 * Created by next on 2022/8/23.
 */
public class Cunsumer {
    public static void main(String[]args) throws IOException {
        HelloService helloService=ProxyFactory.getProxy(HelloService.class);
        System.out.println(helloService.sayHello("12356"));
        HttpClient httpClient= HttpClients.createDefault();
        HttpGet get=new HttpGet("https://baidu.com");
        HttpResponse response=httpClient.execute(get);
        String resoult= EntityUtils.toString(response.getEntity(),"UTF-8");
        System.out.println(resoult);
    }
}
