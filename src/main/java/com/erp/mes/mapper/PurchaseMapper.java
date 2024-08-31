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
//    SELECT * FROM plan
    @Select("""
            SELECT * FROM plan
            
            """)
    List<PlanDTO> plan(); // 조달 계회 리스트
    @Select("""
            select i.name, p.qty, p.leadtime, s.name, value
            from `order` o
            join supplier s on s.sup_id = o.sup_id
            join plan p on p.plan_id = o.plan_id
            join item i on i.item_id = p.plan_id
            """)
    List<OrderDTO> order(); // 구매 발주서 리스트
    @Update("UPDATE `order` SET order_code = #{order_code}, date = #{date}, leadtime = #{leadtime}, status = #{status}, value = #{value} WHERE order_id = #{order_id}")
    int orderForm(Map<String,Object> map); // 구매 발주서 수정
    @Select("SELECT * FROM inspection")
    int inspection(); // 검수 확인
    @Insert("INSERT INTO inspection VALUES(#{ins_id}, NOW(), #{status}, #{notice})")
    int inspectionForm(Map<String, Object> map); // 검수 생성
    @Update("#{order_code}")
    int inspectionUpdate(Map<String, Object> map);
    @Insert("INSERT INTO `order` VALUE(0,#{item_id},#{order_code},date,leadtime,status,value)")
    int orderCreate(Map<String, Object> map);
}
