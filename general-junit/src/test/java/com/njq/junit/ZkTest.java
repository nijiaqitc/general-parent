package com.njq.junit;

import org.apache.log4j.BasicConfigurator;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * @author: nijiaqi
 * @date: 2019/12/4
 */
public class ZkTest {

    public static void main(String[] args) throws Exception {

        //quick log4j config
        BasicConfigurator.configure();

        ZkWatcher zkWatcher = new ZkWatcher();
        Stat stat = new Stat();
        ZooKeeper zk = new ZooKeeper("118.25.12.143:2181", 3000, zkWatcher);
        System.out.println(zk.getState());

        //先等待，连上之后 count减1 ，再创建节点
//        zkWatcher.waitToConn();
//        String path1 = zk.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//        System.out.println("创建一个临时节点----------:" + path1);
//        String path2 = zk.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
//        System.out.println("创建一个临时顺序节点（名称一样 序号会自动叠加）----------:" + path2);

        //异步创建节点： 只要用这个callback方法即是异步创建节点
//        zk.create("/zk-test-ephemeral-11", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
//                CreateMode.PERSISTENT, new IStringCallBack(), "I am context.");


//        zk.create("/zk-test-ephemeral-11/aaa", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
//                CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallBack(), "I am context.");
//        zk.create("/zk-test-ephemeral-11/vvv", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
//                CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallBack(), "I am context.");
//
//        List<String> childrenList = zk.getChildren("/zk-test-ephemeral-11", true);
//        System.out.println("klklklkl!!!!:" + childrenList);



        String path = "/zk-book";
//        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 1
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        /**
         * 参数watch为true ， 即注册一个默认watch（构造函数中的watch）----它是客户端主动拉取节点，
         * 拉取完节点后再把watch注册上去，这个watch是给下个变动用的
         */
        List<String> childrenLis1t = zk.getChildren(path, true);
        System.out.println("kkkkkk@@@1:" + childrenLis1t);
        //2
        zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        childrenLis1t = zk.getChildren(path, true);
        System.out.println("kkkkkk@@@2:" + childrenLis1t);
        //3
        zk.create(path + "/c3", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.getChildren(path + "/c3", true, new IChildren2Callback(), null);
        System.out.println("kkkkkk@@@3:" + childrenLis1t);
        //4
        zk.create(path + "/c4", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//        childrenLis1t = zk.getChildren(path, true);
        //异步获取节点数据
        zk.getChildren(path + "/c4", true, new IChildren2Callback(), null);
        System.out.println("kkkkkk@@@4:" + childrenLis1t);

        zk.create(path + "/abx", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.getData(path + "/abx", true, null);
        Stat stt =  zk.setData(path + "/abx", "abx".getBytes(), -1);
        System.out.println(stt.getCzxid()+","+stt.getMzxid()+","+stt.getVersion());

        Stat stt2 =  zk.setData(path + "/abx", "567".getBytes(), stt.getVersion());
        System.out.println(stt2.getCzxid()+","+stt2.getMzxid()+","+stt2.getVersion());
        try {
            //使用上上个版本的version更新， 基于版本更新CAS 则会更新失败 报错
            zk.setData(path + "/abx", "567".getBytes(), stt.getVersion());
        }catch (Exception ex){
        }
        // version 为-1 代表不需要版本校验，此处为异步更新
        zk.setData(path + "/abx", "qqq".getBytes(),-1,new IStatCallback(),null);

//        zk.delete(path + "/abx",-1 );
//        zk.delete(path + "/abx", -1, new IDeleteNodeCallback(), null);

        //设置权限
        zk.addAuthInfo("digest","foo:true".getBytes());
//        zk.create(path + "/fbx", "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);


//        String path = "/";
//        List<String> children = zk.getChildren(path, null);
//        for (String child : children) {
//            System.out.println("find " + child + " :");
//            System.out.println("   " + new String(zk.getData(path + child, true, stat)));
//        }


//        List<String> childrenLis1t11 = zk.getChildren(path3, true);
//        System.out.println("kkkkkk@@@:"+childrenLis1t11);


        zk.close();

        ZooKeeper zk1 = new ZooKeeper("118.25.12.143:2181", 3000, zkWatcher);
        //设置当前连接的权限
//        zk1.addAuthInfo("digest","foo:true".getBytes());
//        System.out.println(zk1.getData(path + "/fbx", false, null));
        //添加权限后创建的节点可以被其他未授权的zk删除，但要删除这个节点的字节点则必须要--获取权限后才能删除子节点
        zk1.delete(path + "/fbx",-1 );

        zk1.close();
    }

    static class ZkWatcher implements Watcher {
        private CountDownLatch connectedSemaphore = new CountDownLatch(1);

        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println("Receive watched event:" + watchedEvent);
            if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                    System.out.println("ReGet Child:-------------:" +watchedEvent.getPath());
                }else{
                    connectedSemaphore.countDown();
                }
            }
        }

        public void waitToConn() throws Exception {
            connectedSemaphore.await();
        }
    }

    static class IStringCallBack implements AsyncCallback.StringCallback {

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("create path result:[" + rc + ", " + path + ", " + ctx + ", name:" + name);

        }
    }


    static class IChildren2Callback implements AsyncCallback.Children2Callback{
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            System.out.println("get Children znode result:"+rc+" "+path);
        }
    }

    static class IStatCallback implements AsyncCallback.StatCallback{

        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            if(rc == 0){
                System.out.println("SUCCESS");
            }
        }
    }

    static class IDeleteNodeCallback implements AsyncCallback.VoidCallback{

        @Override
        public void processResult(int rc, String path, Object ctx) {
            if(rc == 0){
                System.out.println("SUCCESS11");
            }
        }

    }
}
