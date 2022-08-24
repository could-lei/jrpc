package framwork;

import protocol.Http.HttpProtocol;
import protocol.Netty.NettyProtocol;

/**
 * Created by next on 2022/8/23.
 */
public class ProtocolFactory {
    public static Protocol getProtocol(){
        String name=System.getProperty("protocolName");
        if(name==null||name.equals(""))name="http";
        switch (name){
            case "http":
                return new HttpProtocol();
            case "netty":
                return new NettyProtocol();
        }
        return null;
    }
}
