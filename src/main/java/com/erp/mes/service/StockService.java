package com.erp.mes.service;

import com.erp.mes.dao.StockMapper;
import com.erp.mes.dto.StockDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StockService {

    private final StockMapper stockMapper;


    public StockService(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    public List<StockDTO> getAllStocks() {
        return stockMapper.getAllStocks();
    }

    public StockDTO getStockById(int stk_id) {
        return stockMapper.getStockById(stk_id);
    }

    public int saveStock(StockDTO stock) {
        if (stock.getStk_id() == 0) {
            return stockMapper.insertStock(stock);
        } else {
            return stockMapper.updateStock(stock);
        }
    }

    public int deleteStock(int stk_id) {
        return stockMapper.deleteStock(stk_id);
    }

    public int updateStock(StockDTO stock) {
        return stockMapper.updateStock(stock);
    }
}
