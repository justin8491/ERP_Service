package com.erp.mes.service;

import com.erp.mes.dto.StockReportDTO;
import com.erp.mes.mapper.StockReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StockReportService {
    private final StockReportMapper stockReportMapper;

    public List<StockReportDTO> generateStockReport(Map<String, Object> params) {
        return stockReportMapper.generateStockReport(params);
    }

    public StockReportDTO getStockDetail(int stkId) {
        return stockReportMapper.getStockDetail(stkId);
    }

    public List<StockReportDTO> getPeriodReport(Date startDate, Date endDate) {
        return stockReportMapper.getPeriodReport(startDate, endDate);
    }
}
