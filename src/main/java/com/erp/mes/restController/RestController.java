//package com.erp.mes.restController;
//
//import com.erp.mes.dto.InputDTO;
//import com.erp.mes.dto.OrderDTO;
//import com.erp.mes.dto.TransactionDTO;
//import com.erp.mes.service.InputService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@org.springframework.web.bind.annotation.RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//@Slf4j
//public class RestController {
//    private final InputService service;
//
////    @GetMapping("/list")
////    public String input(Model model) {
////        List<InputDTO> list = service.inputList();
////        model.addAttribute("list",list);
////        log.info("Input List: {}", list);
////        return "ok";
////    }
//
//    @PostMapping("/list")
//    public String inputForm(@RequestBody InputDTO inputDTO) {
//        int n = service.inputForm(inputDTO);
//        log.info("info = {}", n);
//        if(n == 0) {
//            return "추가실패";
//        }else {
//            log.info("toString = {}",inputDTO.toString());
//            return "ok";
//        }
//    }
//
//    @GetMapping("/transaction")
//    public String transactionList(Model model) {
//        List<TransactionDTO> list = service.transactionList();
//        model.addAttribute("list",list);
//        log.info("transaction={}",list.toString());
//        return "ok";
//    }
//    @PostMapping("/transaction")
//    public String transactionForm(@RequestBody TransactionDTO transactionDTO) {
//        int n = service.transactionFrom(transactionDTO);
//        log.info("n={}",n);
//        if(n == 0) {
//            return "없는 거래내역";
//        }
//        log.info("transaction ={}",transactionDTO);
//        return "ok";
//    }
//    @GetMapping("order")
//    public String orderList(Model model) {
//        List<OrderDTO> list = service.orderList();
//        model.addAttribute("list",list);
//        log.info("Order List: {}", list);
//        return "ok";
//    }
//    @PostMapping("order")
//    public String insertOrder(@RequestBody OrderDTO orderDTO) {
//        int n = service.insertOrder(orderDTO);
//        log.info("Insert={}" , orderDTO);
//        return "ok";
//    }
//    @PutMapping("order")
//    public String orderForm(@RequestBody Map<String,Object> map) {
//        int n = service.orderForm(map);
//        log.info("Update count: {}", n);
//        if(n <= 0) {
//            return "없다";
//        }
//        return "ok";
//    }
//    @PostMapping("insepection")
//    public String insepctionForm(
//            String selectedValue,
//            String numValue,
//            Map<String,Object> map
//    ) {
//        log.info("a={} " , selectedValue);
//        log.info("a={} " , numValue);
//        map.put("selectValue",selectedValue);
//        map.put("inputId",numValue);
//        service.updateInputStatus(map);
//        return "OK";
//    }
////    @PostMapping("search")
////    public ResponseEntity<String> searchList(@RequestBody OrderDTO orderDTO) {
////        try {
////            vaildation(orderDTO);
////            List<> list = service.serachList(orderDTO);
////            log.info("list={}",list);
////            return new ResponseEntity<>("검색성공" , HttpStatus.CREATED);
////        }catch(IllegalStateException e) {
////            return new ResponseEntity<>("클라이언트오류",HttpStatus.UNAUTHORIZED);
////        }catch (Exception e) {
////            return new ResponseEntity<>("서버오류",HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
//    private void vaildation(InputDTO inputDTO) {
//        if(inputDTO.getKeyword() == null || inputDTO.getKeyword().isEmpty()) {
//            throw new IllegalStateException("값이 비어있습니다");
//        }
//    }
//}
