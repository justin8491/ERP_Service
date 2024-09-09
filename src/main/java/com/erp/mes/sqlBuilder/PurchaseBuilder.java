package com.erp.mes.sqlBuilder;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class PurchaseBuilder {
    // 조달계획 리스트
    public String selectPlans() {
        return new SQL() {{
            SELECT("p.plan_id, i.name as item_name, p.qty, p.date, p.leadtime, p.status");
            FROM("plan p");
            JOIN("item i on i.item_id = p.item_id");
        }}.toString();
    }

    // 발주서 발행
    public String insertOrder(Map<String, Object> map) {
        return new SQL() {{
            INSERT_INTO("`order`");
            VALUES("sup_id", "#{sup_id}");
            VALUES("plan_id", "#{plan_id}");
            VALUES("order_code", "#{order_code}");
            VALUES("date", "#{date}");
            VALUES("status", "#{status}");
            VALUES("value", "#{value}");
        }}.toString();
    }

    // 발주서 리스트
    public String selectOrders(Map<String, Object> params) {
        return new SQL() {{
            SELECT("o.order_code as orderCode, i.name, p.qty, p.leadtime, s.name AS supName, o.value");
            FROM("`order` o");
            JOIN("supplier s ON s.sup_id = o.sup_id");
            JOIN("plan p ON p.plan_id = o.plan_id");
            JOIN("item i ON i.item_id = p.item_id");
            if(params != null){
                // 공급업체 이름 필터링
                if (params.get("supplier_name") != null) {
                    WHERE("s.name = #{supplier_name}");
                }

                // 날짜 범위 필터링
                if (params.get("startDate") != null) {
                    WHERE("p.leadtime >= #{startDate}");
                }
                if (params.get("endDate") != null) {
                    WHERE("p.leadtime <= #{endDate}");
                }
            }

        }}.toString();
    }

    // 발주서 수정
    public String updateOrder(Map<String, Object> map) {
        return new SQL() {{
            UPDATE("`order`");
            SET("leadtime = #{leadtime}");
            SET("status = #{status}");
            SET("value = #{value}");
            WHERE("order_id = #{order_id}");
        }}.toString();
    }

    // 검수 리스트 확인
    public String selectInspections() {
        return new SQL() {{
            SELECT("*");
            FROM("inspection");
        }}.toString();
    }

    // 검수 등록
    public String insertInspection(Map<String, Object> map) {
        return new SQL() {{
            INSERT_INTO("inspection");
            VALUES("`date`", "NOW()");
            VALUES("status", "#{status}");
            VALUES("notice", "#{notice}");
        }}.toString();
    }

    // 검수 수정
    public String updateInspection(Map<String, Object> map) {
        return new SQL() {{
            UPDATE("inspection");
            SET("status = #{status}");
            SET("notice = #{notice}");
        }}.toString();
    }
}
