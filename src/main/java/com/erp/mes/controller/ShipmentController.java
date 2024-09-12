package com.erp.mes.controller;

import com.erp.mes.dto.ShipmentDTO;
import com.erp.mes.service.ShipmentService;
<<<<<<< Updated upstream
=======
import com.erp.mes.service.StockService;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    // 출고 요청 생성
=======
    private final ShipmentService shipmentService;
    private final StockService stockService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("shipmentDTO", new ShipmentDTO());
        model.addAttribute("stockList", stockService.getStockItemList());
        return "shipment/add";
    }

>>>>>>> Stashed changes
    @PostMapping("/create")
    public String createShipment(@ModelAttribute ShipmentDTO shipmentDTO, Model model) {
        try {
            int result = shipmentService.createShipment(shipmentDTO);
            if (result > 0) {
                return "redirect:/shipment/list";
            } else {
                model.addAttribute("error", "출고 요청 생성에 실패했습니다.");
                return "errorPage";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("stockList", stockService.getStockItemList());
            return "shipment/add";
        }
    }
    @GetMapping("/list")
    public String listShipments(@RequestParam Map<String, Object> params, Model model) {
        List<ShipmentDTO> shipmentList = shipmentService.getShipmentList(params);
        model.addAttribute("shipmentList", shipmentList);
        return "shipment/list";
    }

    @PostMapping("/complete/{shipId}")
    public String completeShipment(@PathVariable int shipId, @RequestParam int qty, Model model) {
        try {
            shipmentService.completeShipment(shipId, qty);
            return "redirect:/shipment/list";
        } catch (RuntimeException e) {
            model.addAttribute("error", "출고 완료 처리 중 오류가 발생했습니다: " + e.getMessage());
            return "errorPage";
        }
    }

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

    @PostMapping("/update-status")
    public String updateShipmentStatus(@RequestParam int shipId, @RequestParam String status, Model model) {
        int result = shipmentService.updateShipmentStatus(shipId, status);
        if (result > 0) {
            return "redirect:/shipment/list";
        } else {
            model.addAttribute("error", "출고 상태 업데이트에 실패했습니다.");
            return "errorPage";
        }
    }

    @GetMapping("/detail/{shipId}")
    public String viewShipmentDetails(@PathVariable("shipId") int shipId, Model model) {
        ShipmentDTO shipment = shipmentService.selectShipmentById(shipId);
        if (shipment == null) {
            model.addAttribute("error", "해당 출고 정보를 찾을 수 없습니다.");
            return "errorPage";
        }
        model.addAttribute("shipment", shipment);
        return "shipment/detail";
    }

    @GetMapping("/search")
    public String searchShipments(@RequestParam Map<String, Object> params, Model model) {
        List<ShipmentDTO> shipmentList = shipmentService.searchShipments(params);
        model.addAttribute("shipmentList", shipmentList);
        return "shipment/list";
    }

    @GetMapping("/check-stock/{shipId}")
    public String checkStock(@PathVariable("shipId") int shipId, Model model) {
        ShipmentDTO shipment = shipmentService.checkStockForShipment(shipId);
        model.addAttribute("shipment", shipment);
        return "shipment/stock-check";
    }

    @PostMapping("/partial-complete/{shipId}")
    public String partialCompleteShipment(@PathVariable int shipId, @RequestParam int qty, Model model) {
        try {
            shipmentService.partialCompleteShipment(shipId, qty);
            return "redirect:/shipment/list";
        } catch (RuntimeException e) {
            model.addAttribute("error", "부분 출고 처리 중 오류가 발생했습니다: " + e.getMessage());
            return "errorPage";
        }
    }

    @PostMapping("/update-quantity/{shipId}")
    public String updateShipmentQuantity(@PathVariable int shipId, @RequestParam int newQty, Model model) {
        try {
            shipmentService.updateShipmentQuantity(shipId, newQty);
            return "redirect:/shipment/detail/" + shipId;
        } catch (RuntimeException e) {
            model.addAttribute("error", "출고 수량 변경 중 오류가 발생했습니다: " + e.getMessage());
            return "errorPage";
        }
    }
}