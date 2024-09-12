//package com.erp.mes.sqlBuilder;
//
//import org.apache.ibatis.jdbc.SQL;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class StockReportBuilderTest {
//
//    private final StockReportBuilder stockReportBuilder = new StockReportBuilder();
//
//    @Test
//    void testSelectStockReportWithoutParams() {
//        Map<String, Object> params = new HashMap<>();
//        String expectedSQL = new SQL() {{
//            SELECT("s.stk_id, i.name AS item_name, s.qty, s.loc, s.value, s.in_date, s.exp_date, s.status");
//            FROM("stock s");
//            JOIN("item i ON s.item_id = i.item_id");
//            ORDER_BY("s.stk_id DESC");
//        }}.toString();
//
//        String generatedSQL = stockReportBuilder.selectStockReport(params);
//        assertEquals(expectedSQL, generatedSQL);
//    }
//
//    @Test
//    void testSelectStockReportWithItemName() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("itemName", "Test Item");
//
//        String expectedSQL = new SQL() {{
//            SELECT("s.stk_id, i.name AS item_name, s.qty, s.loc, s.value, s.in_date, s.exp_date, s.status");
//            FROM("stock s");
//            JOIN("item i ON s.item_id = i.item_id");
//            WHERE("i.name LIKE CONCAT('%', #{itemName}, '%')");
//            ORDER_BY("s.stk_id DESC");
//        }}.toString();
//
//        String generatedSQL = stockReportBuilder.selectStockReport(params);
//        assertEquals(expectedSQL, generatedSQL);
//    }
//
//    @Test
//    void testCalculateStockValueReport() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("startDate", "2024-01-01");
//        params.put("endDate", "2024-12-31");
//
//        String expectedSQL = new SQL() {{
//            SELECT("SUM(s.qty * i.price) AS totalStockValue");
//            FROM("stock s");
//            JOIN("item i ON s.item_id = i.item_id");
//            WHERE("s.in_date >= #{startDate}");
//            WHERE("s.in_date <= #{endDate}");
//        }}.toString();
//
//        String generatedSQL = stockReportBuilder.calculateStockValueReport(params);
//        assertEquals(expectedSQL, generatedSQL);
//    }
//
//    @Test
//    void testSelectStockChangeReport() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("itemName", "Test Item");
//        params.put("status", "AVAILABLE");
//
//        String expectedSQL = new SQL() {{
//            SELECT("s.stk_id, i.name AS item_name, s.qty, s.in_date, s.exp_date, sh.req_qty AS shipment_qty, sh.ship_date");
//            FROM("stock s");
//            JOIN("item i ON s.item_id = i.item_id");
//            LEFT_OUTER_JOIN("shipment sh ON s.stk_id = sh.stk_id");
//            WHERE("i.name LIKE CONCAT('%', #{itemName}, '%')");
//            WHERE("s.status = #{status}");
//            ORDER_BY("s.in_date DESC, sh.ship_date DESC");
//        }}.toString();
//
//        String generatedSQL = stockReportBuilder.selectStockChangeReport(params);
//        assertEquals(expectedSQL, generatedSQL);
//    }
//
//    @Test
//    void testSelectItemSpecificReport() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("itemId", 1);
//
//        String expectedSQL = new SQL() {{
//            SELECT("s.stk_id, i.name AS item_name, s.qty, s.loc, s.value, s.in_date, s.exp_date, s.status");
//            FROM("stock s");
//            JOIN("item i ON s.item_id = i.item_id");
//            WHERE("i.item_id = #{itemId}");
//            ORDER_BY("s.stk_id DESC");
//        }}.toString();
//
//        String generatedSQL = stockReportBuilder.selectItemSpecificReport(params);
//        assertEquals(expectedSQL, generatedSQL);
//    }
//}
