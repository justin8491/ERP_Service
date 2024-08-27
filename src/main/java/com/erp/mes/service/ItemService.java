package com.erp.mes.service;

import com.erp.mes.dao.ItemMapper;
import com.erp.mes.dto.ItemDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {

    private final ItemMapper itemMapper;

    // 생성자 주입 변경
    public ItemService(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public List<ItemDTO> getAllItems() {
        return itemMapper.getAllItems();
    }

    public ItemDTO getItemById(int id) {
        return itemMapper.getItemById(id);
    }

    public int saveItem(ItemDTO item) {
        return itemMapper.insert(item);
    }

    public int deleteItem(int id) {
        return itemMapper.deleteItem(id);
    }

    public int updateItem(ItemDTO item) {
        return itemMapper.update(item);
    }

    public List<ItemDTO> searchItems(String name, String type, Double minPrice, Double maxPrice) {
        return itemMapper.searchItems(name, type, minPrice, maxPrice);
    }
}
