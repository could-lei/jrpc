import framwork.Protocol;
import framwork.ProtocolFactory;
import framwork.URL;
import org.apache.zookeeper.KeeperException;
import register.Register;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {

        URL url=new URL("localhost",8080);
        Register.regist(url, HelloService.class.getName(), HelloServiceImpl.class);

        Protocol protocol= (Protocol) ProtocolFactory.getProtocol();
        protocol.start(url);
    }
}
