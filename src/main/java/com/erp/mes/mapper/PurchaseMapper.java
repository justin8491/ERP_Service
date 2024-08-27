package com.erp.mes.mapper;

import com.erp.mes.dto.InspectionDTO;
import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.PlanDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseMapper {

    @Select("SELECT * FROM plan")
    List<PlanDTO> plan(Map<String,Object> map); // 조달 계회 리스트
    @Select("SELECT * FROM order")
    List<OrderDTO> order(Map<String,Object> map); // 구매 발주서 리스트

    @Update("UPDATE order SET order_code = #{order_code}, date = #{date}, leadtime = #{leadtime}, status = #{status}, value = #{value}")
    int orderForm(Map<String,Object> map); // 구매 발주서 수정
    @Select("")
    int inspection(Map<String,Object> map); // 검수 확인
    @Insert("INSERT INTO inspection")
    int inspectionForm(Map<String, Object> map); // 검수 생성





}
