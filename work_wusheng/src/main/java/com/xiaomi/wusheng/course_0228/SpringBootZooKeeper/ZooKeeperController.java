package com.xiaomi.wusheng.course_0228.SpringBootZooKeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zookeeper")
public class ZooKeeperController {

    @Autowired
    private ZooKeeperService zooKeeperService;

    @PostMapping("/create")
    public String createNode(@RequestParam String path, @RequestParam String data) throws Exception {
        return zooKeeperService.createNode(path, data);
    }

    @GetMapping("/get")
    public String getNodeData(@RequestParam String path) throws Exception {
        return zooKeeperService.getNodeData(path);
    }

    @PutMapping("/update")
    public String updateNodeData(@RequestParam String path, @RequestParam String data) throws Exception {
        zooKeeperService.updateNodeData(path, data);
        return "Node updated successfully!";
    }

    @DeleteMapping("/delete")
    public String deleteNode(@RequestParam String path) throws Exception {
        zooKeeperService.deleteNode(path);
        return "Node deleted successfully!";
    }
}
