package com.erp.mes.restController;

import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.PlanDTO;
import com.erp.mes.service.PurchaseService;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/purchase/*")
public class PurchaseRestController {

    private final PurchaseService purchaseService;


    public PurchaseRestController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    /**
     * 조달계획 리스트 조회
     * - 조회 기준 정하기
     *
     * @param map
     * @return
     */
    @GetMapping(value = "plan")
    public Map<String, Object> plan(Map<String, Object> map) {
        List<PlanDTO> planList = purchaseService.plan(map);
        map.put("planList", planList);
        return map;
    }

    /**
     * 구매발주서 리스트
     * @param map
     * @return
     */
    @GetMapping(value = "order")
    public Map<String, Object> order(Map<String, Object> map) {
        List<OrderDTO> orderList = purchaseService.order(map);
//        map.put("",);
        return map;
    }

    /**
     * 구매발주서 수정
     * @param map
     * @return
     */
    @PostMapping(value = "orderForm")
    public Map<String, Object> orderForm(Map<String, Object> map) {
        int result = purchaseService.orderForm(map);
//        map.put("",);
        return map;
    }

    /**
     * 검수 확인
     * @param map
     * @return
     */
    @GetMapping(value = "inspection")
    public Map<String, Object> inspection(Map<String,Object> map){
        int result = purchaseService.inspection(map);
        return map;
    }

    /**
     * 검수 생성
     * @param map
     * @return
     */
    @PostMapping(value = "inspection")
    public Map<String, Object> inspectionForm(Map<String,Object> map){
        int result = purchaseService.inspectionForm(map);
        return map;
    }


}
