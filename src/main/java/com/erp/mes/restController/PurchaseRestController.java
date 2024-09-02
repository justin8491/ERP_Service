package com.erp.mes.restController;

import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.PlanDTO;
import com.erp.mes.service.PurchaseService;
import org.apache.ibatis.annotations.Select;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {

    private final PurchaseService purchaseService;

    public PurchaseRestController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    /**
     * 조달계획 리스트 조회
     * - 조회 기준 정하기
     *
     * @param
     * @return
     */
    @GetMapping(value = "plan")
    public Map<String, Object> plan() {
        Map<String, Object> map = new HashMap<>();
        List<PlanDTO> planList = purchaseService.plan();
        map.put("planList", planList);
        return map;
    }

    @PostMapping("orderCreate")
    public Map<String, Object> orderCreate(@RequestBody Map<String, Object> map) {
        int result = purchaseService.orderCreate(map);
        return map;
    }

    /**
     * 구매발주서 리스트
     *
     * @param
     * @return
     */
    @GetMapping(value = "order")
    public Map<String, Object> order() {
        Map<String, Object> map = new HashMap<>();
        List<OrderDTO> orderList = purchaseService.order();
        map.put("orderList", orderList);
        return map;
    }

    /**
     * 구매발주서 수정
     *
     * @param map
     * @return
     */
    @PostMapping(value = "orderForm")
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
    @GetMapping(value = "inspection")
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
    @PostMapping(value = "inspection")
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
    @PatchMapping(value = "inspection")
    public Map<String, Object> inspectionUpdate(Map<String, Object> map) {
        int result = purchaseService.inspectionUpdate(map);
        return map;
    }


}
