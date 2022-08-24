package ZookeeperUtil;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ZookeeperUtil implements Watcher {
    private ZooKeeper zoo=null;
    @Override
    public void process(WatchedEvent watchedEvent) {

    }
    public ZookeeperUtil(){
        if(zoo==null){
            try {
                zoo=new ZooKeeper("124.221.117.132:2181", 20000, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        System.out.println("zookeeper连接成功");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void close(){
        try {
            zoo.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * OPEN_ACL_UNSAFE 获取所有权限，
     *  PERSISTENT 永久节点
     *  PERSISTENT_SEQUENTIAL 永久顺序节点
     *  EPHEMERAL  临时节点
     *  EPHEMERAL_SEQUENTIAL 临时顺序节点
     */
    public void createNode(String path,String data) throws KeeperException, InterruptedException {
        if(exitNode(path,false)){
            return;
        }
        if(zoo==null){
            try {
                zoo=new ZooKeeper("124.221.117.132:2181",10000,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        zoo.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
    }
    public boolean exitNode(String path,boolean watcher) throws KeeperException, InterruptedException {
        Stat stat=zoo.exists(path,watcher);
        if(stat==null)return false;
        return true;
    }
    public String getData(String path,boolean watcher) throws UnsupportedEncodingException, KeeperException, InterruptedException {
        if(!exitNode(path,false)){
            return null;
        }
        byte[]stat=zoo.getData(path,watcher,null);
        return new String(stat,"UTF-8");
    }
    public void setData(String path,String data) throws KeeperException, InterruptedException {
        if (!exitNode(path,false)){
            zoo.create(path,data.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }
        Stat stat=zoo.setData(path,data.getBytes(),zoo.exists(path,true).getVersion());
        System.out.println("set data Successfully");
    }
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        return zoo.getChildren(path,null);
    }
    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zoo.delete(path,zoo.exists(path,true).getVersion());
    }
}
