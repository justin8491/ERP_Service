package com.erp.mes.controller;

import com.erp.mes.dto.InputDTO;
import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.PageDTO;
import com.erp.mes.dto.TransactionDTO;
import com.erp.mes.service.InputService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/input")
@Slf4j
public class InputController {

    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }

    private final InputService service;

//    @GetMapping("/inputList")
//    public String input(Model model) {
//        List<InputDTO> list = service.inputList();
////        log.info("list={}",list);
//        model.addAttribute("list",list);
//        return "input/list";
//    }
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
        return "redirect:/input/paging";
    }
    @PostMapping("/search")
    public String search(InputDTO inputDTO,Model model) {
        List<InputDTO> list = service.serachList(inputDTO);
        model.addAttribute("list",list);
        return "input/search";
    }

    @GetMapping("/paging")
    public String paging(Model model,
                        @RequestParam(value = "page", required = false,defaultValue = "1") int page) {
        List<InputDTO> pagingList = service.pagingList(page);
        log.info("page={}",page);
        log.info("={}",pagingList);
        PageDTO pageDTO = service.pagingParam(page);
        model.addAttribute("boardList",pagingList);
        model.addAttribute("paging",pageDTO);
        return "input/list";
    }
    @GetMapping("/insep")
    public String insep(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        List<InputDTO> pagingList = service.pagingListTrue(page);

        log.info("={}",pagingList);
        log.info("page={}", page);

        PageDTO pageDTO = service.pagingParamTrue(page);
        model.addAttribute("insep",pagingList);
        model.addAttribute("paging", pageDTO);
        return "input/insep";
    }
}
