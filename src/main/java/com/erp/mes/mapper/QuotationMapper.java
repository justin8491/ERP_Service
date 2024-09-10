package com.erp.mes.mapper;

import com.erp.mes.sqlBuilder.QuotationBuilder;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface QuotationMapper {

    @InsertProvider(type = QuotationBuilder.class, method = "quoCreate")
    int quoCreate(Map<String, Object> map);
}
