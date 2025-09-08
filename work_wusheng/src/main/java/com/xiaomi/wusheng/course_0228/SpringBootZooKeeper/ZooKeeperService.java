package com.xiaomi.wusheng.course_0228.SpringBootZooKeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZooKeeperService {

    @Autowired
    private ZooKeeper zooKeeper;

    // 创建节点
    public String createNode(String path, String data) throws KeeperException, InterruptedException {
        return zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    // 获取节点数据
    public String getNodeData(String path) throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData(path, false, null);
        return new String(data);
    }

    // 更新节点数据
    public Stat updateNodeData(String path, String data) throws KeeperException, InterruptedException {
        return zooKeeper.setData(path, data.getBytes(), -1);
    }

    // 删除节点
    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, -1);
    }
}
