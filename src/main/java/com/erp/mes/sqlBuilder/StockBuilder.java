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
            SELECT("s.stk_id, i.item_id, i.name AS item_name, i.item_code, " +
                    "s.qty, s.loc, s.value, s.in_date, s.exp_date, " +
                    "s.cons_qty, s.cons_date, s.cons_loc, i.spec, i.price, i.type");
            FROM("stock s");
            JOIN("item i ON s.item_id = i.item_id");
            WHERE("s.cons_qty >= 1");  // 소모 수량(cons_qty)이 1 이상인 항목만 조회
            ORDER_BY("s.stk_id DESC");
        }}.toString();
    }


    // 재고 상세보기 항목 조회
    public String selectStockDetails() {
        return new SQL() {{
            SELECT("s.stk_id, i.item_id, i.name AS item_name, i.item_code, " +
                    "s.qty, s.loc, s.value, s.in_date, s.exp_date, " +
                    "s.cons_qty, s.cons_date, s.cons_loc, i.spec, i.price, i.type");
            FROM("stock s");
            JOIN("item i ON s.item_id = i.item_id");
            WHERE("s.stk_id = #{stkId}"); // 재고 ID로 필터링
        }}.toString();
    }

    public String selectStockList(Map<String, Object> params) {
        return new SQL() {{
            SELECT("s.stk_id, i.name AS item_name, s.qty, s.loc, s.value, s.in_date, s.exp_date, s.status, inp.type AS input_type");
            FROM("stock s");
            JOIN("item i ON s.item_id = i.item_id");
            JOIN("transaction t ON s.item_id = t.plan_id"); // stock과 transaction을 item_id로 연결
            JOIN("input inp ON t.tran_id = inp.tran_id");   // transaction과 input을 tran_id로 연결

            // 품목 이름 필터링
            if (params.get("itemName") != null && !params.get("itemName").toString().isEmpty()) {
                WHERE("i.name LIKE CONCAT('%', #{itemName}, '%')");
            }

            // 입고 검수 상태에 따른 가용/비가용 필터링
            if (params.get("status") != null && !params.get("status").toString().isEmpty()) {
                if (params.get("status").toString().equals("available")) {
                    WHERE("inp.type = true");  // 가용: 검수 통과된 재고
                } else if (params.get("status").toString().equals("unavailable")) {
                    WHERE("inp.type = false"); // 비가용: 검수 통과하지 못한 재고
                }
            }

            // 재고 수량 필터링
            if (params.get("minQty") != null) {
                WHERE("s.qty >= #{minQty}");
            }

            // 입고 날짜 필터링 (startDate, endDate로 날짜 범위 지정) - 날짜가 null 또는 빈 문자열이 아니어야 함
            if (params.get("startDate") != null && !params.get("startDate").toString().isEmpty() &&
                    params.get("endDate") != null && !params.get("endDate").toString().isEmpty()) {
                WHERE("s.in_date BETWEEN #{startDate} AND #{endDate}");
            }

            // 위치 필터링
            if (params.get("loc") != null && !params.get("loc").toString().isEmpty()) {
                WHERE("s.loc LIKE CONCAT('%', #{loc}, '%')");
            }

            // 만료 날짜 필터링 (특정 만료 날짜 이전 재고 조회) - 날짜가 null 또는 빈 문자열이 아니어야 함
            if (params.get("expBeforeDate") != null && !params.get("expBeforeDate").toString().isEmpty()) {
                WHERE("s.exp_date <= #{expBeforeDate}");
            }

            // 정렬
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
    public String calculateStockValue(final Map<String, Object> params) {
        String startDate = (String) params.get("startDate");
        String endDate = (String) params.get("endDate");

        // Subquery for shipment data
        String shipmentSubquery = "(SELECT sh.stk_id, SUM(sh.qty) AS shipped_qty " +
                "FROM shipment sh " +
                "WHERE sh.req_date BETWEEN #{startDate} AND #{endDate} " +
                "GROUP BY sh.stk_id)";

        return new SQL() {{
            SELECT("SUM((s.qty - IFNULL(shipment_data.shipped_qty, 0)) * i.price) AS totalStockValue");
            FROM("stock s");
            JOIN("item i ON s.item_id = i.item_id");
            LEFT_OUTER_JOIN(shipmentSubquery + " shipment_data ON s.stk_id = shipment_data.stk_id");
            WHERE("s.qty - IFNULL(shipment_data.shipped_qty, 0) > 0");
        }}.toString();
    }

    public String getSupplyPrice() {
        return new SQL() {{
            SELECT("s.stk_id, q.price, i.name AS item_name, s.qty, q.quo_id, q.valid_until AS create_date, t.val AS transaction_value, t.date AS transaction_date, t.refer_code");
            FROM("stock s");
            LEFT_OUTER_JOIN("item i ON s.item_id = i.item_id");
            LEFT_OUTER_JOIN("quotation q ON i.item_id = q.item_id");
            LEFT_OUTER_JOIN("transaction t ON t.plan_id = q.quo_id");  // transaction 테이블과 JOIN
            WHERE("s.stk_id = #{stkId}");
            ORDER_BY("q.valid_until DESC");  // 가장 최근의 유효한 견적을 먼저 가져오기 위해
        }}.toString();
    }


    public String selectStockItemList() {
        return new SQL() {{
            SELECT("s.stk_id, i.item_id, i.name AS item_name, i.item_code, " +
                    "s.qty, s.loc, s.value, s.in_date, s.exp_date, " +
                    "s.cons_qty, s.cons_date, s.cons_loc, i.spec, i.price, i.type");
            FROM("stock s");
            JOIN("item i ON s.item_id = i.item_id");
            WHERE("s.qty > 0");  // 재고 수량이 0보다 큰 항목만 조회
            ORDER_BY("s.stk_id DESC");
        }}.toString();
    }

}
