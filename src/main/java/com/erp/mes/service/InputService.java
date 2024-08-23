package com.erp.mes.service;

import com.erp.mes.dto.InputDTO;
import com.erp.mes.dto.TransactionDTO;
import com.erp.mes.mapper.InputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InputService {
    private final InputMapper mapper;
    public List<InputDTO> inputList() {
        return mapper.inputList();
    }
    public List<TransactionDTO> transactionList() {
        return mapper.transactionList();
    }

}
