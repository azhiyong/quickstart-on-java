package xyz.mdou.quickstart.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by zhiyong.li on 2015/10/10.
 */
public class ZkServer {

    private static final String PARENT_NODE_PATH = "/zk_test";
    private static final String ZK_ADDRESS = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(ZK_ADDRESS, 5000, watchedEvent -> {
                //TODO
            });
            if (zooKeeper.exists(PARENT_NODE_PATH, false) == null) {
                zooKeeper.create(PARENT_NODE_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            }
            String actualPath = zooKeeper.create(PARENT_NODE_PATH + "/child_node", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(actualPath);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
