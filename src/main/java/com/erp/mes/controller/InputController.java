package com.erp.mes.controller;

import com.erp.mes.dto.InputDTO;
import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.TransactionDTO;
import com.erp.mes.service.InputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/input")
@Slf4j
public class InputController {
    private final InputService service;

    @GetMapping("/list")
    public String input(Model model) {
        List<InputDTO> list = service.inputList();
        model.addAttribute("list",list);
        return "input/list";
    }
//    @PostMapping("/list")
//    public String inputForm() {
//        int n = service.inputForm();
//        log.info("info = {}", n);
//        if(n == 0) {
//            return "검수아직다안됨";
//        }else {
//            return "ok";
//        }
//    }

    @GetMapping("/transaction")
    public String transactionList(Model model) {
        List<TransactionDTO> list = service.transactionList();
        model.addAttribute("list",list);
        return "ok";
    }
//    @PostMapping("/transaction")
//    public String transactionForm(Map<String,Object> map) {
//        int n = service.transactionFrom(map);
//        log.info("n={}",n);
//        if(n == 0) {
//            return "없는 거래내역페이지";
//        }
//        return "ok";
//    }
    @GetMapping("order")
    public String orderList(Model model) {
        List<OrderDTO> list = service.orderList();
        model.addAttribute("list",list);
        log.info("Order List: {}", list);
        return "ok";
    }
    @PostMapping("order")
    public String orderForm(Map<String,Object> map) {
        int n = service.orderForm(map);
        log.info("Update count: {}", n);
        if(n <= 0) {
            return "없다";
        }
        return "ok";
    }
}
