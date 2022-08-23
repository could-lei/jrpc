package protocol.Http;

import framework.Invocation;
import framework.Protocol;
import framework.URL;

/**
 * Created by next on 2022/8/23.
 */
public class HttpProtocol implements Protocol{
    public void start(URL url) {
        HttpServer server=new HttpServer();
        server.start(url.getHostname(),url.getPort());
    }

    public String send(URL url, Invocation invocation) {
        HttpCient client=new HttpCient();

        return client.post(url.getHostname(),url.getPort(),invocation);

    }
}
