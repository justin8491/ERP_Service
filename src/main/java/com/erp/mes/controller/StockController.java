package com.erp.mes.controller;

import com.erp.mes.dto.StockDTO;
import com.erp.mes.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/detail/{stk_id}")
    public String getStockById(@PathVariable("stk_id") int stk_id, Model model) {
        StockDTO stock = stockService.getStockById(stk_id);
        model.addAttribute("stock", stock);
        return "stock/stockDetail"; // 상세 페이지 반환
    }

    @PostMapping("/delete/{stk_id}")
    public String deleteStock(@PathVariable("stk_id") int stk_id, Model model) {
        int result = stockService.deleteStock(stk_id);
        if (result > 0) {
            return "redirect:/stock/stockList"; // 성공 시 목록 리다이렉트
        } else {
            model.addAttribute("errorMessage", "삭제에 실패했습니다.");
            return "stock/stockDetail"; // 실패 시 상세 페이지로 돌아감
        }
    }

    @GetMapping("/insert")
    public String showInsertForm(Model model) {
        model.addAttribute("stock", new StockDTO());
        return "stock/stockInsert"; // 추가 폼 이동
    }

    @PostMapping("/save")
    public String saveStock(@ModelAttribute StockDTO stock, Model model) {
        int result = stockService.saveStock(stock);
        if (result > 0) {
            return "redirect:/stock/stockList"; // 성공 시 목록 리다이렉트
        } else {
            model.addAttribute("errorMessage", "저장에 실패했습니다.");
            return "stock/stockInsert"; // 실패 시 추가 폼으로 돌아감
        }
    }

    @GetMapping("/stockList")
    public String stockList(Model model) {
        List<StockDTO> stocks = stockService.getAllStocks();
        model.addAttribute("stocks", stocks);
        return "stock/stockList";
    }

    @PostMapping("/update/{stk_id}")
    public String updateStock(@PathVariable("stk_id") int stk_id, @ModelAttribute StockDTO stock, Model model) {
        stock.setStk_id(stk_id);
        int result = stockService.saveStock(stock);
        if (result > 0) {
            return "redirect:/stock/stockList"; // 성공 시 목록 리다이렉트
        } else {
            model.addAttribute("errorMessage", "수정에 실패했습니다.");
            return "stock/stockEdit"; // 실패 시 수정 폼으로 돌아감
        }
    }

    @GetMapping("/edit/{stk_id}")
    public String showEditForm(@PathVariable("stk_id") int stk_id, Model model) {
        StockDTO stock = stockService.getStockById(stk_id);
        model.addAttribute("stock", stock);
        return "stock/stockEdit";
    }
}
