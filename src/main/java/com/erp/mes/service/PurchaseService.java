package com.erp.mes.service;

import com.erp.mes.mapper.PurchaseDAO;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final PurchaseDAO purchaseDAO;


    public PurchaseService(PurchaseDAO purchaseDAO) {
        this.purchaseDAO = purchaseDAO;
    }
}
