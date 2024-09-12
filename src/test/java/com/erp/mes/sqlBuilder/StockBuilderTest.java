//package com.erp.mes.sqlBuilder;
//
//import org.apache.ibatis.jdbc.SQL;
//import org.junit.jupiter.api.Test;
//import java.util.HashMap;
//import java.util.Map;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class StockBuilderTest {
//
//    private final StockBuilder stockBuilder = new StockBuilder();
//
//    // 입고 완료된 자재를 재고로 반영하는 쿼리 테스트
//    @Test
//    public void testInsertStockFromCompletedInput() {
//        String expected = new SQL() {{
//            INSERT_INTO("stock");
//            SELECT("i.item_id, i.qty, i.loc, i.value, i.in_date, i.exp_date, 'AVAILABLE' AS status");
//            FROM("input i");
//            WHERE("i.type = 1");
//        }}.toString();
//
//        String result = stockBuilder.insertStockFromCompletedInput();
//        assertEquals(expected, result);
//    }
//
//    // 재고 목록 조회 쿼리 테스트 (필터링 없이)
//    @Test
//    public void testSelectStockListNoParams() {
//        Map<String, Object> params = new HashMap<>();
//
//        String expected = new SQL() {{
//            SELECT("s.stk_id, i.name AS item_name, s.qty, s.loc, s.value, s.in_date, s.exp_date, s.status");
//            FROM("stock s");
//            JOIN("item i ON s.item_id = i.item_id");
//            ORDER_BY("s.stk_id DESC");
//        }}.toString();
//
//        String result = stockBuilder.selectStockList(params);
//        assertEquals(expected, result);
//    }
//
//    // 재고 목록 조회 쿼리 테스트 (itemName 필터링)
//    @Test
//    public void testSelectStockListWithItemName() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("itemName", "Test Item");
//
//        String expected = new SQL() {{
//            SELECT("s.stk_id, i.name AS item_name, s.qty, s.loc, s.value, s.in_date, s.exp_date, s.status");
//            FROM("stock s");
//            JOIN("item i ON s.item_id = i.item_id");
//            WHERE("i.name LIKE CONCAT('%', #{itemName}, '%')");
//            ORDER_BY("s.stk_id DESC");
//        }}.toString();
//
//        String result = stockBuilder.selectStockList(params);
//        assertEquals(expected, result);
//    }
//
//    // 재고 목록 조회 쿼리 테스트 (status 필터링)
//    @Test
//    public void testSelectStockListWithStatus() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("status", "AVAILABLE");
//
//        String expected = new SQL() {{
//            SELECT("s.stk_id, i.name AS item_name, s.qty, s.loc, s.value, s.in_date, s.exp_date, s.status");
//            FROM("stock s");
//            JOIN("item i ON s.item_id = i.item_id");
//            WHERE("s.status = #{status}");
//            ORDER_BY("s.stk_id DESC");
//        }}.toString();
//
//        String result = stockBuilder.selectStockList(params);
//        assertEquals(expected, result);
//    }
//
//    // 재고 목록 조회 쿼리 테스트 (itemName과 status 필터링)
//    @Test
//    public void testSelectStockListWithItemNameAndStatus() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("itemName", "Test Item");
//        params.put("status", "AVAILABLE");
//
//        String expected = new SQL() {{
//            SELECT("s.stk_id, i.name AS item_name, s.qty, s.loc, s.value, s.in_date, s.exp_date, s.status");
//            FROM("stock s");
//            JOIN("item i ON s.item_id = i.item_id");
//            WHERE("i.name LIKE CONCAT('%', #{itemName}, '%')");
//            WHERE("s.status = #{status}");
//            ORDER_BY("s.stk_id DESC");
//        }}.toString();
//
//        String result = stockBuilder.selectStockList(params);
//        assertEquals(expected, result);
//    }
//
//    // 출고 후 재고 수량 업데이트 테스트
//    @Test
//    public void testUpdateStockAfterShipment() {
//        String expected = new SQL() {{
//            UPDATE("stock");
//            SET("qty = qty - #{qty}");
//            WHERE("stk_id = #{stkId}");
//        }}.toString();
//
//        String result = stockBuilder.updateStockAfterShipment();
//        assertEquals(expected, result);
//    }
//
//    // 재고 상태 업데이트 테스트
//    @Test
//    public void testUpdateStockStatus() {
//        String expected = new SQL() {{
//            UPDATE("stock");
//            SET("status = #{status}");
//            WHERE("stk_id = #{stkId}");
//        }}.toString();
//
//        String result = stockBuilder.updateStockStatus();
//        assertEquals(expected, result);
//    }
//
//    // 재고 금액 산출 쿼리 테스트
//    @Test
//    public void testCalculateStockValue() {
//        String expected = new SQL() {{
//            SELECT("SUM(s.qty * i.price) AS totalStockValue");
//            FROM("stock s");
//            JOIN("item i ON s.item_id = i.item_id");
//        }}.toString();
//
//        String result = stockBuilder.calculateStockValue();
//        assertEquals(expected, result);
//    }
//
//    // 공급가 확인 쿼리 테스트
//    @Test
//    public void testGetSupplyPrice() {
//        String expected = new SQL() {{
//            SELECT("i.item_id, i.name, sup.supply_price");
//            FROM("item i");
//            JOIN("supplier sup ON i.supplier_id = sup.supplier_id");
//            WHERE("i.item_id = #{itemId}");
//        }}.toString();
//
//        String result = stockBuilder.getSupplyPrice();
//        assertEquals(expected, result);
//    }
//}
