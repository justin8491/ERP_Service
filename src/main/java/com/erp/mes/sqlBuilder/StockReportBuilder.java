package com.erp.mes.sqlBuilder;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class StockReportBuilder {

    // 전체 재고 상태 보고서 (기본 재고 수량 및 상태 조회)
    public String selectStockReport(Map<String, Object> params) {
        return new SQL() {{
            SELECT("s.stk_id, i.name AS item_name, s.qty, s.loc, s.value, s.in_date, s.exp_date, s.status");
            FROM("stock s");
            JOIN("item i ON s.item_id = i.item_id");
            if (params.get("itemName") != null && !params.get("itemName").toString().isEmpty()) {
                WHERE("i.name LIKE CONCAT('%', #{itemName}, '%')");
            }
            if (params.get("status") != null && !params.get("status").toString().isEmpty()) {
                WHERE("s.status = #{status}");
            }
            ORDER_BY("s.stk_id DESC");
        }}.toString();
    }

    // 특정 기간 동안의 재고 금액 산출 보고서
    public String calculateStockValueReport(Map<String, Object> params) {
        return new SQL() {{
            SELECT("SUM(s.qty * i.price) AS totalStockValue");
            FROM("stock s");
            JOIN("item i ON s.item_id = i.item_id");
            WHERE("s.in_date >= #{startDate}");
            WHERE("s.in_date <= #{endDate}");
        }}.toString();
    }

    // 재고 증감 보고서 (출고와 입고에 따른 재고 증감 내역)
    public String selectStockChangeReport(Map<String, Object> params) {
        return new SQL() {{
            SELECT("s.stk_id, i.name AS item_name, s.qty, s.in_date, s.exp_date, sh.req_qty AS shipment_qty, sh.ship_date");
            FROM("stock s");
            JOIN("item i ON s.item_id = i.item_id");
            // LEFT_JOIN 대신 LEFT OUTER JOIN 사용
            LEFT_OUTER_JOIN("shipment sh ON s.stk_id = sh.stk_id");
            if (params.get("itemName") != null && !params.get("itemName").toString().isEmpty()) {
                WHERE("i.name LIKE CONCAT('%', #{itemName}, '%')");
            }
            if (params.get("status") != null && !params.get("status").toString().isEmpty()) {
                WHERE("s.status = #{status}");
            }
            ORDER_BY("s.in_date DESC, sh.ship_date DESC");
        }}.toString();
    }


    // 특정 품목의 재고 상태 보고서
    public String selectItemSpecificReport(Map<String, Object> params) {
        return new SQL() {{
            SELECT("s.stk_id, i.name AS item_name, s.qty, s.loc, s.value, s.in_date, s.exp_date, s.status");
            FROM("stock s");
            JOIN("item i ON s.item_id = i.item_id");
            WHERE("i.item_id = #{itemId}");
            ORDER_BY("s.stk_id DESC");
        }}.toString();
    }
}
