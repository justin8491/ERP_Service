package com.erp.mes.sqlBuilder;
import org.apache.ibatis.jdbc.SQL;
import java.util.Map;

public class ItemBuilder {
    // 품목 목록 조회 (검색 필터 포함, 자동 완성 가능)

    public String selectItemList(Map<String, Object> params) {
        return new SQL() {{
            SELECT("item_id, name, spec, unit, price, create_date");
            FROM("item");
            String keyword = (String) params.get("keyword");
            if (keyword != null && !keyword.isEmpty()) {
                WHERE("name LIKE CONCAT('%', #{keyword}, '%')");
            }
            ORDER_BY("name ASC");
        }}.toString();
    }
    // 품목 삽입
    public String insertItem() {
        return new SQL() {{
            INSERT_INTO("item");
            VALUES("name", "#{name}");
            VALUES("spec", "#{spec}");
            VALUES("unit", "#{unit}");
            VALUES("price", "#{price}");
            VALUES("create_date", "NOW()");
        }}.toString();
    }
    // 품목 수정
    public String updateItem() {
        return new SQL() {{
            UPDATE("item");
            SET("name = #{name}");
            SET("spec = #{spec}");
            SET("unit = #{unit}");
            SET("price = #{price}");
            WHERE("item_id = #{itemId}");
        }}.toString();
    }
    // 품목 삭제
    public String deleteItem() {
        return new SQL() {{
            DELETE_FROM("item");
            WHERE("item_id = #{itemId}");
        }}.toString();
    }
    // 유효성 검사 (중복된 품목명이나 필수 입력 누락 여부)
    public String validateItemData(Map<String, Object> params) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("item");
            WHERE("name = #{name}");
            AND();
            WHERE("spec = #{spec}");
        }}.toString();
    }
    // 품목 상세 조회
    public String selectItemById() {
        return new SQL() {{
            SELECT("item_id, name, spec, unit, price, create_date");
            FROM("item");
            WHERE("item_id = #{itemId}");
        }}.toString();
    }
}