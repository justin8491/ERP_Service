package com.erp.mes.dao;

import com.erp.mes.dto.ItemDTO;
import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.PlanDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseDAO {

    @Select("SELECT * FROM plan")
    List<PlanDTO> plan(PlanDTO planDTO); // 조달 계회 리스트
    List<OrderDTO> order(OrderDTO orderDTO); // 구매 발주서 리스트

    int orderForm(OrderDTO orderDTO); // 구매 발주서 수정

    int inspectionForm(Map<String, Object> map);

    int inspection(InspectionDTO inspectionDTO);



}
