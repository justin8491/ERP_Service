package com.erp.mes.service;

import com.erp.mes.dto.StockReportDTO;
import com.erp.mes.mapper.StockReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StockReportService {
    private final StockReportMapper stockReportMapper;

    // 전체 재고 상태 보고서
    public List<StockReportDTO> getStockReport(Map<String, Object> params) {
        return stockReportMapper.selectStockReport(params);
    }

    // 특정 기간 동안의 재고 금액 산출 보고서
    public Double calculateStockValueReport(String startDate, String endDate) {
        return stockReportMapper.calculateStockValueReport(startDate, endDate);
    }

    // 재고 증감 보고서
    public List<StockReportDTO> getStockChangeReport(Map<String, Object> params) {
        return stockReportMapper.selectStockChangeReport(params);
    }

    // 특정 품목의 재고 상태 보고서
    public List<StockReportDTO> getItemSpecificReport(int itemId) {
        return stockReportMapper.selectItemSpecificReport(itemId);
    }
}
