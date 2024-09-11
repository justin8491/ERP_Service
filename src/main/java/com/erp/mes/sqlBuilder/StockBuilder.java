package com.erp.mes.sqlBuilder;

import org.apache.ibatis.jdbc.SQL;
import java.util.Map;

public class StockBuilder {

    // 입고 완료된 자재를 재고로 반영 (입고 수량)
    public String insertStockFromCompletedInput() {
        return new SQL() {{
            INSERT_INTO("stock");
            SELECT("i.item_id, i.qty, i.loc, i.value, i.in_date, i.exp_date, 'AVAILABLE' AS status");
            FROM("input i");
            WHERE("i.type = 1");  // 입고 완료된 항목만 선택
        }}.toString();
    }

    // 재고 목록 조회 (발주 품목 등록)
    public String stockList() {
        return new SQL() {{
            SELECT("i.item_id, i.name, i.item_code, s.cons_qty, s.cons_date," +
                    " s.cons_loc, i.spec, i.price, i.type");
            FROM("stock s");
            JOIN("item i on s.item_id = i.item_id");
            WHERE("s.cons_qty >= 1"); // cons_qty가 1 이상인 조건 추가
        }}.toString();
    }

    // 재고 목록 조회 (필터링 처리, 실물 재고와 가용 재고 구분)
    public String selectStockList(Map<String, Object> params) {
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

    // 출고 후 재고 수량 업데이트 및 기록 처리 (출고 수량)
    public String updateStockAfterShipment() {
        return new SQL() {{
            UPDATE("stock");
            SET("qty = qty - #{qty}");
            WHERE("stk_id = #{stkId}");
        }}.toString();
    }

    // 재고 상태 업데이트
    public String updateStockStatus() {
        return new SQL() {{
            UPDATE("stock");
            SET("status = #{status}");
            WHERE("stk_id = #{stkId}");
        }}.toString();
    }

    // 재고 금액 산출 (기간별 재고 금액 산출)
    public String calculateStockValue() {
        return new SQL() {{
            SELECT("SUM(s.qty * i.price) AS totalStockValue");
            FROM("stock s");
            JOIN("item i ON s.item_id = i.item_id");
        }}.toString();
    }

    // 공급가 확인
    public String getSupplyPrice() {
        return new SQL() {{
            SELECT("i.item_id, i.name, sup.supply_price");
            FROM("item i");
            JOIN("supplier sup ON i.supplier_id = sup.supplier_id");
            WHERE("i.item_id = #{itemId}");
        }}.toString();
    }
}
