package com.erp.mes.dao;

import com.erp.mes.dto.ItemDTO;
import com.erp.mes.dto.PlanDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseDAO {
    /**
     *
     * @param planDTO
     * @return
     */
    List<PlanDTO> plan(PlanDTO planDTO);
}
