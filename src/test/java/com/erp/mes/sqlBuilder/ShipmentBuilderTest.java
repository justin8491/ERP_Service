package com.erp.mes.sqlBuilder;

import org.apache.ibatis.jdbc.SQL;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipmentBuilderTest {

    private final ShipmentBuilder shipmentBuilder = new ShipmentBuilder();

    @Test
    void testInsertShipment() {
        String result = shipmentBuilder.insertShipment();
        String expected = new SQL() {{
            INSERT_INTO("shipment");
            VALUES("stk_id", "#{stkId}");
            VALUES("req_date", "#{reqDate}");
            VALUES("req_qty", "#{reqQty}");
            VALUES("status", "'REQUESTED'");
        }}.toString();
        assertEquals(expected, result);
    }

    @Test
    void testSelectShipmentListWithoutParams() {
        Map<String, Object> params = new HashMap<>();
        String result = shipmentBuilder.selectShipmentList(params);
        String expected = new SQL() {{
            SELECT("sh.ship_id, sh.req_date, sh.req_qty, s.item_id, i.name AS item_name, s.qty AS available_qty, s.loc, sh.status");
            FROM("shipment sh");
            JOIN("stock s ON sh.stk_id = s.stk_id");
            JOIN("item i ON s.item_id = i.item_id");
            ORDER_BY("sh.req_date DESC");
        }}.toString();
        assertEquals(expected, result);
    }

    @Test
    void testSelectShipmentListWithParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("itemName", "SampleItem");
        params.put("status", "REQUESTED");

        String result = shipmentBuilder.selectShipmentList(params);
        String expected = new SQL() {{
            SELECT("sh.ship_id, sh.req_date, sh.req_qty, s.item_id, i.name AS item_name, s.qty AS available_qty, s.loc, sh.status");
            FROM("shipment sh");
            JOIN("stock s ON sh.stk_id = s.stk_id");
            JOIN("item i ON s.item_id = i.item_id");
            WHERE("i.name LIKE CONCAT('%', #{itemName}, '%')");
            WHERE("sh.status = #{status}");
            ORDER_BY("sh.req_date DESC");
        }}.toString();

        assertEquals(expected, result);
    }

    @Test
    void testUpdateShipmentStatusToCompleted() {
        String result = shipmentBuilder.updateShipmentStatusToCompleted();
        String expected = new SQL() {{
            UPDATE("shipment");
            SET("status = 'COMPLETED'");
            SET("ship_date = NOW()");
            WHERE("ship_id = #{shipId}");
        }}.toString();
        assertEquals(expected, result);
    }

    @Test
    void testCancelShipment() {
        String result = shipmentBuilder.cancelShipment();
        String expected = new SQL() {{
            UPDATE("shipment");
            SET("status = 'CANCELLED'");
            WHERE("ship_id = #{shipId}");
        }}.toString();
        assertEquals(expected, result);
    }

    @Test
    void testUpdateShipmentStatus() {
        String result = shipmentBuilder.updateShipmentStatus();
        String expected = new SQL() {{
            UPDATE("shipment");
            SET("status = #{status}");
            WHERE("ship_id = #{shipId}");
        }}.toString();
        assertEquals(expected, result);
    }

    @Test
    void testUpdateStockAfterShipment() {
        String result = shipmentBuilder.updateStockAfterShipment();
        String expected = new SQL() {{
            UPDATE("stock");
            SET("qty = qty - #{reqQty}");
            WHERE("stk_id = #{stkId}");
        }}.toString();
        assertEquals(expected, result);
    }
}
