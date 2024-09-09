package com.erp.mes.mapper;

import com.erp.mes.dto.StockReportDTO;
import com.erp.mes.sqlBuilder.StockReportBuilder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockReportMapper {

    // 전체 재고 상태 보고서
    @SelectProvider(type = StockReportBuilder.class, method = "selectStockReport")
    List<StockReportDTO> selectStockReport(Map<String, Object> params);

    // 특정 기간 동안의 재고 금액 산출 보고서
    @SelectProvider(type = StockReportBuilder.class, method = "calculateStockValueReport")
    Double calculateStockValueReport(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 재고 증감 보고서 (출고 및 입고에 따른 재고 증감 내역)
    @SelectProvider(type = StockReportBuilder.class, method = "selectStockChangeReport")
    List<StockReportDTO> selectStockChangeReport(Map<String, Object> params);

    // 특정 품목의 재고 상태 보고서
    @SelectProvider(type = StockReportBuilder.class, method = "selectItemSpecificReport")
    List<StockReportDTO> selectItemSpecificReport(@Param("itemId") int itemId);
}
