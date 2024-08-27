package com.erp.mes.service;

import com.erp.mes.dto.InspectionDTO;
import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.PlanDTO;
import com.erp.mes.mapper.PurchaseMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.rtf.RTFEditorKit;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseService {

    private final PurchaseMapper purchaseMapper;

    public PurchaseService(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }
    // 조달 계획 리스트
    public List<PlanDTO> plan(Map<String,Object> map){
        return purchaseMapper.plan(map);
    }
    // 구매 발주서 리스트
    public List<OrderDTO> order(Map<String,Object> map){
        return purchaseMapper.order(map);
    }
    // 구매 발주서 수정
    public int orderForm(Map<String,Object> map){
        return purchaseMapper.orderForm(map);
    }

    // 검수 확인
    public int inspection(Map<String,Object> map){
        return purchaseMapper.inspection(map);
    }

    // 검수 생성
    public int inspectionForm(Map<String,Object> map){
        return purchaseMapper.inspectionForm(map);
    }





}
