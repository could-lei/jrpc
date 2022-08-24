package register;

import ZookeeperUtil.ZookeeperUtil;
import com.alibaba.fastjson.JSON;


import framwork.URL;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {
    public final static HashMap<String, Map<URL,Class>> REGISTER=new HashMap<String, Map<URL, Class>>();
    public final static HashMap<String,Class>mem=new HashMap<>();
    public static void regist(URL url,String interfaceName,Class implClass) throws KeeperException, InterruptedException, IOException {
        ZookeeperUtil zookeeperUtil=new ZookeeperUtil();
        String path="/"+interfaceName;
        zookeeperUtil.createNode(path,"");
        path=path+"/"+ JSON.toJSONString(url);
        zookeeperUtil.createNode(path,JSON.toJSONString(implClass));
    }
    public static URL get(String interfaceName){
        return null;
    }
    public static Class get(URL url, String interfaceName) throws InterruptedException, UnsupportedEncodingException, KeeperException, ClassNotFoundException {
        ZookeeperUtil zookeeperUtil=new ZookeeperUtil();
        String result=zookeeperUtil.getData("/"+interfaceName,false);
        List<String> list=zookeeperUtil.getChildren("/"+interfaceName);

//        return REGISTER.get(interfaceName).get(url);
        for(int i=0;i<list.size();i++){
            if(JSON.toJSONString(url).equals(list.get(i))){
                String clazz=zookeeperUtil.getData("/"+interfaceName+"/"+list.get(i),false);
                return JSON.parseObject(clazz,Class.class);
            }
        }
        return null;
    }
    public static URL random(String interfaceName) throws KeeperException, InterruptedException {
        ZookeeperUtil zookeeperUtil=new ZookeeperUtil();
        List<String>list=zookeeperUtil.getChildren("/"+interfaceName);
        int index= (int) Math.random()*list.size();
        String url=list.get(index);
        return JSON.parseObject(url,URL.class);
    }
}

