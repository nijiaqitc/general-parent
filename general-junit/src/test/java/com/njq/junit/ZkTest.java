package com.njq.junit;

import org.apache.log4j.BasicConfigurator;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * @author: nijiaqi
 * @date: 2019/12/4
 */
public class ZkTest {

    public static void main(String[] args) throws Exception{

        //quick log4j config
        BasicConfigurator.configure();

        ZkWatcher zkWatcher = new ZkWatcher();
        Stat stat = new Stat();
        ZooKeeper zk = new ZooKeeper("118.25.12.143:2181", 3000, zkWatcher);
        zkWatcher.waitToConn();

        System.out.println(zk.getState());

        String path = "/";
        List<String> children = zk.getChildren(path, null);
        for (String child : children) {
            System.out.println("find " + child + " :");
            System.out.println("   " + new String(zk.getData(path + child, true, stat)));
        }

        zk.close();


    }

    static class ZkWatcher implements Watcher {
        private  CountDownLatch connectedSemaphore = new CountDownLatch(1);

        @Override
        public void process(WatchedEvent watchedEvent) {
            if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                connectedSemaphore.countDown();
            }
        }

        public void waitToConn() throws Exception {
            connectedSemaphore.await();
        }
    }

}
