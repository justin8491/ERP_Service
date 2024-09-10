package com.erp.mes.controller;

import com.erp.mes.dto.ShipmentDTO;
import com.erp.mes.service.ShipmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shipment")
public class ShipmentController {
    private final ShipmentService shipmentService;

    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }

    // 출고 요청 생성
    @PostMapping("/create")
    public String createShipment(ShipmentDTO shipmentDTO, Model model) {
        int result = shipmentService.createShipment(shipmentDTO);
        if (result > 0) {
            return "redirect:/shipment/list";
        } else {
            model.addAttribute("error", "출고 요청 생성에 실패했습니다.");
            return "errorPage";
        }
    }

    // 출고 목록 조회 (필터링 처리)
    @GetMapping("/list")
    public String listShipments(@RequestParam Map<String, Object> params, Model model) {
        List<ShipmentDTO> shipmentList = shipmentService.getShipmentList(params);
        model.addAttribute("shipmentList", shipmentList);
        return "shipment/list";
    }

    // 출고 완료 처리
    @PostMapping("/complete/{shipId}")
    public String completeShipment(@PathVariable int shipId, Model model) {
        int result = shipmentService.completeShipment(shipId);
        if (result > 0) {
            return "redirect:/shipment/list";
        } else {
            model.addAttribute("error", "출고 완료 처리에 실패했습니다.");
            return "errorPage";
        }
    }

    // 출고 요청 취소
    @PostMapping("/cancel/{shipId}")
    public String cancelShipment(@PathVariable int shipId, Model model) {
        int result = shipmentService.cancelShipment(shipId);
        if (result > 0) {
            return "redirect:/shipment/list";
        } else {
            model.addAttribute("error", "출고 요청 취소에 실패했습니다.");
            return "errorPage";
        }
    }

    // 출고 상태 업데이트
    @PostMapping("/updatestatus")
    public String updateShipmentStatus(@RequestParam int shipId, @RequestParam String status, Model model) {
        int result = shipmentService.updateShipmentStatus(shipId, status);
        if (result > 0) {
            return "redirect:/shipment/list";
        } else {
            model.addAttribute("error", "출고 상태 업데이트에 실패했습니다.");
            return "errorPage";
        }
    }

    // 출고 시 재고 차감
    @PostMapping("/updatestock")
    public String updateStockAfterShipment(@RequestParam int stkId, @RequestParam int reqQty, Model model) {
        int result = shipmentService.updateStockAfterShipment(stkId, reqQty);
        if (result > 0) {
            return "redirect:/shipment/list";
        } else {
            model.addAttribute("error", "재고 차감에 실패했습니다.");
            return "errorPage";
        }
    }
}
