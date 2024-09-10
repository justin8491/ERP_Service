package com.erp.mes.sqlBuilder;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ShipmentBuilder {

    // 출고 요청 생성 (출고할 재고 선택 후 수량 설정)
    public String insertShipment() {
        return new SQL() {{
            INSERT_INTO("shipment");
            VALUES("stk_id", "#{stkId}");
            VALUES("req_date", "#{reqDate}");
            VALUES("req_qty", "#{reqQty}");
            VALUES("status", "'REQUESTED'");  // 출고 요청 상태로 등록
        }}.toString();
    }

    // 출고 목록 조회 (필터링 처리, 출고 상태에 따른 조회)
    public String selectShipmentList(Map<String, Object> params) {
        return new SQL() {{
            SELECT("sh.ship_id, sh.req_date, sh.req_qty, s.item_id, i.name AS item_name, s.qty AS available_qty, s.loc, sh.status");
            FROM("shipment sh");
            JOIN("stock s ON sh.stk_id = s.stk_id");
            JOIN("item i ON s.item_id = i.item_id");
            if (params.get("itemName") != null && !params.get("itemName").toString().isEmpty()) {
                WHERE("i.name LIKE CONCAT('%', #{itemName}, '%')");
            }
            if (params.get("status") != null && !params.get("status").toString().isEmpty()) {
                WHERE("sh.status = #{status}");
            }
            ORDER_BY("sh.req_date DESC");
        }}.toString();
    }

    // 출고 완료 처리 (출고 상태 변경 및 출고 수량 업데이트)
    public String updateShipmentStatusToCompleted() {
        return new SQL() {{
            UPDATE("shipment");
            SET("status = 'COMPLETED'");
            SET("ship_date = NOW()");
            WHERE("ship_id = #{shipId}");
        }}.toString();
    }

    // 출고 요청 취소 (상태 변경)
    public String cancelShipment() {
        return new SQL() {{
            UPDATE("shipment");
            SET("status = 'CANCELLED'");
            WHERE("ship_id = #{shipId}");
        }}.toString();
    }

    // 출고 요청 상태 업데이트 (승인 또는 진행 중 처리)
    public String updateShipmentStatus() {
        return new SQL() {{
            UPDATE("shipment");
            SET("status = #{status}");
            WHERE("ship_id = #{shipId}");
        }}.toString();
    }

    // 출고 요청 시 재고 차감
    public String updateStockAfterShipment() {
        return new SQL() {{
            UPDATE("stock");
            SET("qty = qty - #{reqQty}");
            WHERE("stk_id = #{stkId}");
        }}.toString();
    }
}
