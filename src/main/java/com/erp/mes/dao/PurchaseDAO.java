package com.erp.mes.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseDAO {
    List<PlanDTO> plan(ItemDTO itemDTO, PlanDTO planDTO);
}
