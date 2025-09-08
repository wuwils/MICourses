package com.xiaomi.wusheng.work_0227.service;

import com.xiaomi.wusheng.work_0227.model.Item;
import com.xiaomi.wusheng.work_0227.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public List<Item> searchByName(String query) {
        return itemRepository.findByNameContainingIgnoreCase(query);
    }
}
