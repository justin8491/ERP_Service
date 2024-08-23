package com.erp.mes.restController;

import com.erp.mes.dto.InputDTO;
import com.erp.mes.dto.TransactionDTO;
import com.erp.mes.service.InputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("input")
@Slf4j
public class InputController {
    private final InputService service;

    @GetMapping("/inputList")
    public String input(Model model) {
        List<InputDTO> list = service.inputList();
        model.addAttribute("list",list);
        log.info("Input List: {}", list);
        return "ok";
    }

    @PostMapping("/inputList")
    public String inputForm() {
        return "ok";
    }

    @GetMapping("/transaction")
    public String transactionList(Model model) {
        List<TransactionDTO> list = service.transactionList();
        model.addAttribute("list",list);
        return "ok";
    }
}
