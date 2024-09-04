package com.erp.mes.controller;

import com.erp.mes.dto.InputDTO;
import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.TransactionDTO;
import com.erp.mes.service.InputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/input")
@Slf4j
public class InputController {
    private final InputService service;

    @GetMapping("/inputList")
    public String input(Model model) {
        List<InputDTO> list = service.inputList();
//        log.info("list={}",list);
        model.addAttribute("list",list);
        return "input/list";
    }
    @PostMapping("/inputList")
    public String inputForm(InputDTO inputDTO) {
        int n = service.inputForm(inputDTO);
        if(n > 0) {
            return "input/inputList";
        }else {
            return "401";
        }
    }
    @PostMapping("/inseception")
    public String inseceptionForm(
            @RequestParam("numVal") String numValue,
            @RequestParam("inspectionStatus") String inspectionStatus,
            Model model,
            Map<String,Object> map
    ) {
        log.info("a={} " , inspectionStatus);
        log.info("a={} " , numValue);
        map.put("selectValue",inspectionStatus);
        map.put("inputId",numValue);
        service.updateInputStatus(map);
        return "redirect:/input/inputList";
    }
}
