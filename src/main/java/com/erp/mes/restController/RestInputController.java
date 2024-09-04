package com.erp.mes.restController;

import com.erp.mes.dto.InputDTO;
import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.TransactionDTO;
import com.erp.mes.service.InputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class RestInputController {
    private final InputService service;

    @GetMapping("/inputList")
    public String input(Model model) {
        List<InputDTO> list = service.inputList();
        model.addAttribute("list",list);
        log.info("Input List: {}", list);
        return "ok";
    }

    @PostMapping("/inputList")
    public String inputForm(InputDTO inputDTO) {
        int n = service.inputForm(inputDTO);
        log.info("info = {}", n);
        if(n == 0) {
            return "검수아직다안됨";
        }else {
            return "ok";
        }
    }

    @GetMapping("/transaction")
    public String transactionList(Model model) {
        List<TransactionDTO> list = service.transactionList();
        model.addAttribute("list",list);
        return "ok";
    }
    @PostMapping("/transaction")
    public String transactionForm(@RequestBody TransactionDTO transactionDTO) {
        int n = service.transactionFrom(transactionDTO);
        log.info("n={}",n);
        if(n == 0) {
            return "없는 거래내역";
        }
        return "ok";
    }
    @GetMapping("order")
    public String orderList(Model model) {
        List<OrderDTO> list = service.orderList();
        model.addAttribute("list",list);
        log.info("Order List: {}", list);
        return "ok";
    }
    @PostMapping("order")
    public String orderForm(@RequestBody Map<String,Object> map) {
        int n = service.orderForm(map);
        log.info("Update count: {}", n);
        if(n <= 0) {
            return "없다";
        }
        return "ok";
    }
}
