package com.erp.mes.controller;

import com.erp.mes.dto.StockDTO;
import com.erp.mes.service.StockService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        // 필터링 조건이 있는 경우, Map에 추가됨
        List<StockDTO> stockList = stockService.getStockList(params);
        if (stockList != null && !stockList.isEmpty()) {
            model.addAttribute("stockList", stockList);
        } else {
            model.addAttribute("error", "재고 목록을 찾을 수 없습니다.");
        }
        return "stock/list";
    }

    // 출고 후 재고 수량 업데이트
    @PostMapping("/updateaftershipment")
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
    @PostMapping("/updatestatus")
    public String updateStockStatus(@RequestParam int stkId, @RequestParam String status, Model model) {
        int result = stockService.updateStockStatus(stkId, status);
        if (result > 0) {
            return "redirect:/stock/list";
        } else {
            model.addAttribute("error", "재고 상태 업데이트에 실패했습니다.");
            return "errorPage";
        }
    }

    // 재고 금액 산출 (기간별 재고 금액 산출)
    @GetMapping("/calculate-value")
    public ResponseEntity<Map<String, Object>> calculateStockValue(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        // 시작 날짜와 종료 날짜를 이용해 재고 금액 계산 로직 수행
        double totalStockValue = stockService.calculateStockValue(startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("totalStockValue", totalStockValue);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{stkId}")
    public String viewStockDetails(@PathVariable("stkId") int stkId, Model model) {
        StockDTO stock = stockService.getStockDetails(stkId);
        if (stock == null) {
            model.addAttribute("error", "해당 재고 정보를 찾을 수 없습니다.");
            return "errorPage"; // 또는 오류 메시지 표시
        }
        model.addAttribute("stock", stock);
        return "stock/detail";
    }
    @GetMapping("/price/{stkId}")
    public String viewPrice(@PathVariable("stkId") int stkId, Model model) {
        List<Map<String, Object>> priceList = stockService.getPrice(stkId);
        System.out.println(priceList); // 디버깅을 위한 로그 출력
        if (priceList != null && !priceList.isEmpty()) {
            model.addAttribute("priceList", priceList);
        } else {
            model.addAttribute("error", "가격 정보를 찾을 수 없습니다.");
        }
        return "stock/price";
    }
}