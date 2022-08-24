package framwork;

import protocol.Http.HttpProtocol;

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
//                break;
//            case "netty":

        }
        return null;
    }
}
