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

    @Select("""
            SELECT * FROM plan
            """)
    List<PlanDTO> plan(); // 조달 계획 리스트

    @Insert("""
            INSERT INTO `order`(sup_id, plan_id, order_code, `date`, status, value) 
            VALUE(#{item_id},#{order_code},date,leadtime,status,value)
            """)
    int orderCreate(Map<String, Object> map); // 구매 발주서 발행

    @Select("""
            select i.name, p.qty, p.leadtime, s.name, value
            from `order` o
            join supplier s on s.sup_id = o.sup_id
            join plan p on p.plan_id = o.plan_id
            join item i on i.item_id = p.plan_id
            p.leadtime BETWEEN #{startDate} AND #{endDate}
            AND s.name = #{supplier_name}
            """)
    List<OrderDTO> order(); // 구매 발주서 리스트 - 필수 값 -> 납기, 협력회사 이름

    @Update("""
            UPDATE `order` SET 
            leadtime = #{leadtime}, status = #{status}, value = #{value} 
            WHERE order_id = #{order_id}
            """)
    int orderForm(Map<String, Object> map); // 구매 발주서 수정 - 납기, 상태, 금액 부분 변경 - 이외 사항 발주 새로 발행

    @Select("SELECT * FROM inspection")
    int inspection(); // 검수 확인

    @Insert("""
            INSERT INTO inspection(`date`, status, notice) VALUES(NOW(), #{status}, #{notice})
            """)
    int inspectionForm(Map<String, Object> map); // 검수 생성

    @Update("""
            UPDATE inspection SET
            status = #{status}, notice = #{notice}
            """)
    int inspectionUpdate(Map<String, Object> map); // 검수 업데이트


}
