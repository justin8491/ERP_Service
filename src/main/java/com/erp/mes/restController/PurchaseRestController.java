package com.erp.mes.restController;

import com.erp.mes.dto.EmailDTO;
import com.erp.mes.dto.PlanDTO;
import com.erp.mes.dto.SupplierDTO;
import com.erp.mes.service.MailService;
import com.erp.mes.service.PurchaseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PurchaseRestController {

    private final PurchaseService purchaseService;

    private final MailService mailService;

    public PurchaseRestController(PurchaseService purchaseService, MailService mailService) {
        this.purchaseService = purchaseService;
        this.mailService = mailService;
    }

    //    @GetMapping(value = "purchase/plan")
    //    public String getPlan(){
    //        return "purchase/plan";
    //    }

    /**
     * 조달계획 리스트 조회
     * - 조회 기준 정하기
     *
     * @param
     * @return
     */
    @PostMapping(value = "purchase/plan")
    public Map<String, Object> plan() {
        Map<String, Object> map = new HashMap<>();
        List<PlanDTO> planList = purchaseService.plan();
        map.put("planList", planList);
        return map;
    }


    // 발주서 발행
    @PostMapping("purchase/orderCreate")
    public Map<String, Object> orderCreate(@RequestBody Map<String, Object> map) {
        int result = purchaseService.orderCreate(map);
//        if(res)
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setTargetMail(((String)map.get("targetMail")));
        if(mailService.sendMail(emailDTO)){
            map.put("msg","메일 발송 성공");
        } else {
            map.put("msg","메일 발송 실패");
        }
        return map;
    }

    /**
     * 구매발주서 리스트
     *
     * @param
     * @return
     */
//    @GetMapping(value = "purchase/order")
//    public Map<String, Object> order() {
//        Map<String, Object> map = new HashMap<>();
//        List<OrderDTO> orderList = purchaseService.order();
//        map.put("orderList", orderList);
//        return map;
//    }

    /**
     * 구매발주서 수정
     *
     * @param map
     * @return
     */
    @PostMapping(value = "purchase/orderForm")
    public Map<String, Object> orderForm(@RequestBody Map<String, Object> map) {
        int result = purchaseService.orderForm(map);
        map.put("result", result);
        return map;
    }

    /**
     * 검수 확인
     *
     * @param
     * @return
     */
    @GetMapping(value = "purchase/inspection")
    public Map<String, Object> inspection() {
        Map<String, Object> map = new HashMap<>();
        int result = purchaseService.inspection();
        return map;
    }

    /**
     * 검수 생성
     *
     * @param map
     * @return
     */
    @PostMapping(value = "purchase/inspection")
    public Map<String, Object> inspectionForm(Map<String, Object> map) {
        int result = purchaseService.inspectionForm(map);
        return map;
    }

    /**
     * 검수 수정
     *
     * @param map
     * @return
     */
    @PatchMapping(value = "purchase/inspection")
    public Map<String, Object> inspectionUpdate(Map<String, Object> map) {
        int result = purchaseService.inspectionUpdate(map);
        return map;
    }

    @PostMapping(value = "getSupplier")
    public Map<String,Object> getSupplier(Map<String, Object> map){
        List<SupplierDTO> supplierDTO = purchaseService.getSupplier();

        return map;
    }

}
