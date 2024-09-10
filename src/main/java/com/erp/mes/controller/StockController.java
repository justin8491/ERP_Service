package com.erp.mes.controller;

import com.erp.mes.dto.StockDTO;
import com.erp.mes.service.StockService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }


    // 입고 완료된 자재를 재고로 반영
    @PostMapping("/insert")
    public String insertStockFromCompletedInput(Model model) {
        int result = stockService.insertStockFromCompletedInput();
        if (result > 0) {
            return "redirect:/stock/list";
        } else {
            model.addAttribute("error", "입고 완료된 자재를 재고로 반영하는 데 실패했습니다.");
            return "errorPage";
        }
    }

    // 재고 목록 조회 (필터링 가능)
    @GetMapping("/list")
    public String listStock(@RequestParam Map<String, Object> params, Model model) {
        List<StockDTO> stockList = stockService.getStockList(params);
        model.addAttribute("stockList", stockList);
        return "stock/list";
    }

    // 출고 후 재고 수량 업데이트
    @PostMapping("/update-after-shipment")
    public String updateStockAfterShipment(@RequestParam int stkId, @RequestParam int qty, Model model) {
        int result = stockService.updateStockAfterShipment(stkId, qty);
        if (result > 0) {
            return "redirect:/stock/list";
        } else {
            model.addAttribute("error", "재고 수량 업데이트에 실패했습니다.");
            return "errorPage";
        }
    }

    // 재고 상태 업데이트
    @PostMapping("/update-status")
    public String updateStockStatus(@RequestParam int stkId, @RequestParam String status, Model model) {
        int result = stockService.updateStockStatus(stkId, status);
        if (result > 0) {
            return "redirect:/stock/list";
        } else {
            model.addAttribute("error", "재고 상태 업데이트에 실패했습니다.");
            return "errorPage";
        }
    }

    // 재고 금액 산출
    @GetMapping("/calculate-value")
    public String calculateStockValue(Model model) {
        double totalStockValue = stockService.calculateStockValue();
        model.addAttribute("totalStockValue", totalStockValue);
        return "stock/value";
    }

    // 공급가 확인
    @GetMapping("/supply-price/{itemId}")
    @ResponseBody
    public Map<String, Object> getSupplyPrice(@PathVariable int itemId) {
        return stockService.getSupplyPrice(itemId);
    }
}
