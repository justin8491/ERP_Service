package com.erp.mes.service;

import com.erp.mes.mapper.QuotationMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuotationService {

    private final QuotationMapper quotationMapper;

    public QuotationService(QuotationMapper quotationMapper) {
        this.quotationMapper = quotationMapper;
    }

    public int quoCreate(Map<String, Object> map){
        return quotationMapper.quoCreate(map);
    };
}
