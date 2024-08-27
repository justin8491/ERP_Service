package com.erp.mes.service;

import com.erp.mes.mapper.PurchaseMapper;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final PurchaseMapper purchaseMapper;


    public PurchaseService(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }




}
