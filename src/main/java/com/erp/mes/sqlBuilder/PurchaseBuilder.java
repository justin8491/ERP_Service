package com.erp.mes.sqlBuilder;

import org.apache.ibatis.jdbc.SQL;
import java.util.Map;

public class PurchaseBuilder {
    public String selectPlans() {
        return new SQL() {{
            SELECT("p.plan_id, i.name as item_name, p.qty, p.date, p.leadtime, p.status");
            FROM("plan p");
            JOIN("item i on i.item_id = p.item_id");
        }}.toString();
    }

    public String insertOrder(Map<String, Object> map) {
        return new SQL() {{
            INSERT_INTO("`order`");
            VALUES("sup_id", "#{sup_id}");
            VALUES("plan_id", "#{plan_id}");
            VALUES("order_code", "#{order_code}");
            VALUES("date", "#{date}");
            VALUES("status", "#{status}");
            VALUES("value", "#{value}");
        }}.toString();
    }

    public String selectOrders(Map<String, Object> params) {
        return new SQL() {{
            SELECT("i.name, p.qty, p.leadtime, s.name AS supplierName, o.value");
            FROM("`order` o");
            JOIN("supplier s ON s.sup_id = o.sup_id");
            JOIN("plan p ON p.plan_id = o.plan_id");
            JOIN("item i ON i.item_id = p.item_id");
            WHERE("p.leadtime BETWEEN #{startDate} AND #{endDate}");
            WHERE("s.name = #{supplier_name}");
        }}.toString();
    }

    public String updateOrder(Map<String, Object> map) {
        return new SQL() {{
            UPDATE("`order`");
            SET("leadtime = #{leadtime}");
            SET("status = #{status}");
            SET("value = #{value}");
            WHERE("order_id = #{order_id}");
        }}.toString();
    }

    public String selectInspections() {
        return new SQL() {{
            SELECT("*");
            FROM("inspection");
        }}.toString();
    }

    public String insertInspection(Map<String, Object> map) {
        return new SQL() {{
            INSERT_INTO("inspection");
            VALUES("`date`", "NOW()");
            VALUES("status", "#{status}");
            VALUES("notice", "#{notice}");
        }}.toString();
    }

    public String updateInspection(Map<String, Object> map) {
        return new SQL() {{
            UPDATE("inspection");
            SET("status = #{status}");
            SET("notice = #{notice}");
        }}.toString();
    }
}
