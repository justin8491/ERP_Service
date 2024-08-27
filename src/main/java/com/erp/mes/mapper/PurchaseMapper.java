package com.erp.mes.mapper;

import com.erp.mes.dto.InspectionDTO;
import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.PlanDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseMapper {

    @Select("SELECT * FROM plan")
    List<PlanDTO> plan(PlanDTO planDTO); // 조달 계회 리스트
    List<OrderDTO> order(OrderDTO orderDTO); // 구매 발주서 리스트

    int orderForm(OrderDTO orderDTO); // 구매 발주서 수정
 
    int inspectionForm(Map<String, Object> map); // 검수 생성

    int inspection(InspectionDTO inspectionDTO); // 검수 확인



}
