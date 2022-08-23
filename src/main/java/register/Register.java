package register;

import framework.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by next on 2022/8/23.
 */
public class Register {
    public final static HashMap<String,Map<URL,Class>>REGISTER=new HashMap<String, Map<URL, Class>>();
    public static void regist(URL url,String interfaceName,Class implClass){
        Map<URL,Class>map=new HashMap<URL, Class>();
        map.put(url,implClass);
        REGISTER.put(interfaceName,map);
    }
    public static URL get(String interfaceName){
        return null;
    }
    public static Class get(URL url, String interfaceName){
        return REGISTER.get(interfaceName).get(url);
    }
    public static URL random(String interfaceName){
        return REGISTER.get(interfaceName).keySet().iterator().next();
    }
}
