package com.erp.mes.controller;

import com.erp.mes.dto.StockReportDTO;
import com.erp.mes.service.StockReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stockreport")
public class StockReportController {
    private final StockReportService stockReportService;

    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }

    // 전체 재고 상태 보고서
    @GetMapping("/list")
    public String getStockReport(@RequestParam Map<String, Object> params, Model model) {
        List<StockReportDTO> stockReportList = stockReportService.getStockReport(params);
        model.addAttribute("stockReportList", stockReportList);
        return "stockreport/list";
    }

    // 특정 기간 동안의 재고 금액 산출 보고서
    @GetMapping("/calculate")
    public String calculateStockValueReport(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model) {
        Double totalStockValue = stockReportService.calculateStockValueReport(startDate, endDate);
        model.addAttribute("totalStockValue", totalStockValue);
        return "stockreport/value";
    }

    // 재고 증감 보고서
    @GetMapping("/changes")
    public String getStockChangeReport(@RequestParam Map<String, Object> params, Model model) {
        List<StockReportDTO> stockChangeList = stockReportService.getStockChangeReport(params);
        model.addAttribute("stockChangeList", stockChangeList);
        return "stockreport/changes";
    }

    // 특정 품목의 재고 상태 보고서
    @GetMapping("/item/{itemId}")
    public String getItemSpecificReport(@PathVariable int itemId, Model model) {
        List<StockReportDTO> itemSpecificReport = stockReportService.getItemSpecificReport(itemId);
        model.addAttribute("itemSpecificReport", itemSpecificReport);
        return "stockreport/item";
    }
}
