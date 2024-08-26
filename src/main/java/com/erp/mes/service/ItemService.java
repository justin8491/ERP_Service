package com.erp.mes.service;

import com.erp.mes.dao.ItemMapper;
import com.erp.mes.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemMapper itemMapper;

    public List<ItemDTO> getAllItems() {
        return itemMapper.getAllItems();
    }

    public ItemDTO getItemById(int id) {
        return itemMapper.getItemById(id);
    }

    public void saveItem(ItemDTO item) {
        itemMapper.insert(item);
    }

    public void deleteItem(int id) {
        itemMapper.deleteItem(id);
    }

    public void updateItem(ItemDTO item) {
        itemMapper.update(item);
    }

    public List<ItemDTO> searchItems(String name, String type, Double minPrice, Double maxPrice) {
        return itemMapper.searchItems(name, type, minPrice, maxPrice);
    }
}
