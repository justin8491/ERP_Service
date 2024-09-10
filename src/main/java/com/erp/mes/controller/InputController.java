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

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/input")
@Slf4j
public class InputController {
    private final InputService service;

    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }

//    @GetMapping("/inputList")
//    public String input(Model model) {
//        List<InputDTO> list = service.inputList();
//        log.info("list={}",list);
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
    @GetMapping("/bom")
    public String bom(Model model) {
        List<OrderDTO> orders = service.selectOrders();
        log.info("orders={}",orders);
        model.addAttribute("orders",orders);
        return "input/bom";
    }
    @GetMapping("/transaction")
    public String transaction(Model model) {
        List<OrderDTO> trans = service.selectTran();
        List<OrderDTO> transList = service.selectTranList();
        model.addAttribute("trans",trans);
        model.addAttribute("list",transList);
        return "input/transaction";
    }
    @PostMapping("/transaction")
    public String transactionForm(
            @RequestParam("orderCode") String[] orderCode,
            @RequestParam("transSelect") String[] transSelect,
            Map<String, Object> map) {

        if (orderCode == null || orderCode.length == 0 || transSelect == null || transSelect.length == 0 || orderCode.length != transSelect.length) {
            map.put("error", "잘못된 요청입니다.");
            return "error";
        }

        // 상태와 발주코드를 그룹화
        Map<String, List<String>> groupedByStatus = new HashMap<>();
        for (int i = 0; i < orderCode.length; i++) {
            String code = orderCode[i];
            String status = transSelect[i];

            if (code != null && status != null) {
                groupedByStatus
                        .computeIfAbsent(status, k -> new ArrayList<>())
                        .add(code);
            }
        }

        // 발주마감 상태로 일괄 업데이트 처리
        if (groupedByStatus.containsKey("발주마감")) {
            List<String> codesToUpdate = groupedByStatus.get("발주마감");
            int n = service.updateTrans(codesToUpdate);
            log.info("업데이트된 건수: {}", n);
        }

        return "redirect:/input/transaction";
    }

}
