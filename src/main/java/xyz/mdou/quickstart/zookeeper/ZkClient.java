package xyz.mdou.quickstart.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * Created by zhiyong.li on 2015/10/10.
 */
public class ZkClient {

    private static final String PARENT_NODE_PATH = "/zk_test";
    private static final String ZK_ADDRESS = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static ZooKeeper zooKeeper;

    public static void main(String[] args) {
        try {
            zooKeeper = new ZooKeeper(ZK_ADDRESS, 5000, event -> {
                if (event.getType().equals(Watcher.Event.EventType.NodeChildrenChanged) && event.getPath().equals("/")) {
                    updateChildren();
                }
            });
            updateChildren();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateChildren() {
        try {
            List<String> children = zooKeeper.getChildren(PARENT_NODE_PATH, true);
            System.out.println(children);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
