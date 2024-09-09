package com.erp.mes.sqlBuilder;

import org.apache.ibatis.jdbc.SQL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

public class ItemBuilderTest {

    private ItemBuilder testObject = new ItemBuilder();

    @Test
    void shouldRejectNullParams() {
        assertThrows(IllegalArgumentException.class, () -> testObject.selectItemList(null));
    }

    @Test
    void shouldSelectAllWithoutParams() {
        Map<String, Object> params = new HashMap<>();
        String result = testObject.selectItemList(params);
        String expected = new SQL() {{
            SELECT("i.item_id, i.name, i.spec, i.unit, i.price, i.create_date, s.name AS supplierName, p.leadtime");
            FROM("item i");
            JOIN("plan p ON p.item_id = i.item_id");
            JOIN("supplier s ON s.sup_id = p.sup_id");
            ORDER_BY("i.name ASC");
        }}.toString();
        assertEquals(expected, result);
    }

    @Test
    void shouldLimitToItemId() {
        Map<String, Object> params = new HashMap<>();
        params.put("itemId", 1);
        String result = testObject.selectItemList(params);
        String expected = new SQL() {{
            SELECT("i.item_id, i.name, i.spec, i.unit, i.price, i.create_date, s.name AS supplierName, p.leadtime");
            FROM("item i");
            JOIN("plan p ON p.item_id = i.item_id");
            JOIN("supplier s ON s.sup_id = p.sup_id");
            WHERE("i.item_id = #{itemId}");
            ORDER_BY("i.name ASC");
        }}.toString();
        assertEquals(expected, result);
    }

    @Test
    void shouldLimitToKeyword() {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", "test");
        String result = testObject.selectItemList(params);
        String expected = new SQL() {{
            SELECT("i.item_id, i.name, i.spec, i.unit, i.price, i.create_date, s.name AS supplierName, p.leadtime");
            FROM("item i");
            JOIN("plan p ON p.item_id = i.item_id");
            JOIN("supplier s ON s.sup_id = p.sup_id");
            WHERE("i.name LIKE CONCAT('%', #{keyword}, '%')");
            ORDER_BY("i.name ASC");
        }}.toString();
        assertEquals(expected, result);
    }

    // additional test methods following the same pattern...
}