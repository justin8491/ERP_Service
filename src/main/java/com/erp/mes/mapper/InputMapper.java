package com.erp.mes.mapper;

import com.erp.mes.dto.InputDTO;
import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.TransactionDTO;
import com.erp.mes.sqlBuilder.InputBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface InputMapper {

    // 입고리스트
    @SelectProvider(type = InputBuilder.class, method = "buildSelectInput")
    List<InputDTO> inputList();

    // 입고수정
    @UpdateProvider(type = InputBuilder.class, method = "buildUpdateInput")
    int inputForm(InputDTO inputDTO);

    // 거래 명세서
    @SelectProvider(type = InputBuilder.class,method = "buildSelectTransaction")
    List<TransactionDTO> transactionList();

    // 거래명세서 수정
    @UpdateProvider(type = InputBuilder.class, method = "buildUpdateTransaction")
    int transactionForm(TransactionDTO transactionDTO);
    // 발주서 넣기
    @InsertProvider(type = InputBuilder.class,method = "buildInsertOrder")
    int insertOrder(OrderDTO orderDTO);
    // 구매발주서 리스트
    @SelectProvider(type = InputBuilder.class, method = "buildSelectOrder")
    List<OrderDTO> orderList();

    // 구매발주서 확인
    @UpdateProvider(type = InputBuilder.class, method = "buildUpdateOrder")
    int orderForm(Map<String,Object> map);

    // 검수
    //    update문 where status = 100형식으로 다가가야할듯
    @UpdateProvider(type = InputBuilder.class, method = "buildUpdateInputStatus")
    int updateInputStatus(Map<String,Object> map);

    // 검색
    @SelectProvider(type = InputBuilder.class, method = "buildSearch")
    List<InputDTO> serachList(InputDTO inputDTO);

    @SelectProvider(type = InputBuilder.class, method = "buildPaging")
    List<InputDTO> pagingList(Map<String, Object> map );

    @SelectProvider(type = InputBuilder.class, method = "buildPageCount")
    int boardCount();
}