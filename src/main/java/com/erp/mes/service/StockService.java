package com.erp.mes.service;


import com.erp.mes.dto.ItemDTO;
import com.erp.mes.dto.StockDTO;
import com.erp.mes.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockMapper stockMapper;

    // 입고 완료된 자재를 재고로 반영
    public int insertStockFromCompletedInput() {
        return stockMapper.insertStockFromCompletedInput();
    }

    // 재고 목록 조회 (필터링 가능)
    public List<StockDTO> getStockList(Map<String, Object> params) {
        return stockMapper.selectStockList(params);
    }

    // 출고 후 재고 수량 업데이트
    public int updateStockAfterShipment(int stkId, int qty) {
        return stockMapper.updateStockAfterShipment(stkId, qty);
    }

    // 재고 상태 업데이트
    public int updateStockStatus(int stkId, String status) {
        return stockMapper.updateStockStatus(stkId, status);
    }

    // 재고 금액 산출
    public double calculateStockValue() {
        return stockMapper.calculateStockValue();
    }

    // 공급가 확인
    public Map<String, Object> getSupplyPrice(int itemId) {
        return stockMapper.getSupplyPrice(itemId);
    }

    public List<StockDTO> getStockItemList() {
        return stockMapper.selectStockItemList();
    }
}
