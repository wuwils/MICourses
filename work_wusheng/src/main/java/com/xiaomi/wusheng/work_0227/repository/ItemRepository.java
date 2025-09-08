package com.xiaomi.wusheng.work_0227.repository;

import com.xiaomi.wusheng.work_0227.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameContainingIgnoreCase(String query);
}
