package com.njq.junit;

import org.apache.log4j.BasicConfigurator;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

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
        zkWatcher.waitToConn();
        String path1 = zk.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("创建一个临时节点----------:" + path1);
        String path2 = zk.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("创建一个临时顺序节点（名称一样 序号会自动叠加）----------:" + path2);

        zk.create("/zk-test-ephemeral-11", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, new IStringCallBack(), "I am context.");
        zk.create("/zk-test-ephemeral-11", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, new IStringCallBack(), "I am context.");
//        String path = "/";
//        List<String> children = zk.getChildren(path, null);
//        for (String child : children) {
//            System.out.println("find " + child + " :");
//            System.out.println("   " + new String(zk.getData(path + child, true, stat)));
//        }

        zk.close();


    }

    static class ZkWatcher implements Watcher {
        private CountDownLatch connectedSemaphore = new CountDownLatch(1);

        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println("Receive watched event:" + watchedEvent);
            if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                connectedSemaphore.countDown();
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
}
