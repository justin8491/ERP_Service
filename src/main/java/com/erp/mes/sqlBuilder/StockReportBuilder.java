package com.erp.mes.sqlBuilder;

import org.apache.ibatis.jdbc.SQL;
import java.util.Map;

public class StockReportBuilder {
    public String generateStockReport(Map<String, Object> params) {
        return new SQL() {{
            SELECT("s.stk_id, s.item_id, i.name AS itemName, s.qty AS totalQty, " +
                    "s.loc AS location, s.exp_date AS expirationDate, s.cons_qty AS remainingQty, " +
                    "q.price AS unitPrice, s.value AS totalValue, s.in_date AS date");
            FROM("stock s");
            INNER_JOIN("item i ON s.item_id = i.item_id");
            LEFT_OUTER_JOIN("quotation q ON i.item_id = q.item_id");
            WHERE("s.in_date BETWEEN #{startDate} AND #{endDate}");
            if (params.containsKey("itemId") && params.get("itemId") != null) {
                WHERE("s.item_id = #{itemId}");
            }
            ORDER_BY("s.in_date DESC");
        }}.toString();
    }

    public String getStockDetail(int stkId) {
        return new SQL() {{
            SELECT("s.stk_id, s.item_id, i.name AS itemName, i.item_code AS itemCode, s.qty AS totalQty, " +
                    "s.loc AS location, s.exp_date AS expirationDate, s.cons_qty AS remainingQty, " +
                    "q.price AS unitPrice, s.value AS totalValue, s.in_date AS date");
            FROM("stock s");
            INNER_JOIN("item i ON s.item_id = i.item_id");
            LEFT_OUTER_JOIN("quotation q ON i.item_id = q.item_id");
            WHERE("s.stk_id = #{stkId}");
        }}.toString();
    }

    public String getPeriodReport() {
        return new SQL() {{
            SELECT("s.in_date AS date, i.name AS itemName, SUM(s.qty) AS totalQty, " +
                    "SUM(s.value) AS totalValue");
            FROM("stock s");
            INNER_JOIN("item i ON s.item_id = i.item_id");
            WHERE("s.in_date BETWEEN #{startDate} AND #{endDate}");
            GROUP_BY("s.in_date, i.name");
            ORDER_BY("s.in_date DESC");
        }}.toString();
    }
}