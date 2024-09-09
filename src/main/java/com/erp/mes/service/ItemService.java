package com.erp.mes.service;


import com.erp.mes.dto.ItemDTO;
import com.erp.mes.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;

    // 품목 목록 조회
    public List<ItemDTO> selectItemList(String keyword) {
        return itemMapper.selectItemList(keyword);
    }

    // 품목 삽입
    public int insertItem(ItemDTO itemDTO) {
        // 유효성 검사
        if (itemMapper.validateItemData(itemDTO.getName(), itemDTO.getSpec()) > 0) {
            throw new IllegalArgumentException("이미 존재하는 품목입니다.");
        }
        return itemMapper.insertItem(itemDTO);
    }

    // 품목 수정
    public int updateItem(ItemDTO itemDTO) {
        return itemMapper.updateItem(itemDTO);
    }

    // 품목 삭제
    public int deleteItem(int itemId) {
        return itemMapper.deleteItem(itemId);
    }

    // 품목 상세 조회
    public ItemDTO selectItemById(int itemId) {
        return itemMapper.selectItemById(itemId);
    }
}
