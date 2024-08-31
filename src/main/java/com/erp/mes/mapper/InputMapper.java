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
    @SelectProvider(type = InputBuilder.class, method = "buildSelectInputList")
    List<InputDTO> inputList();

    // 입고수정
    @UpdateProvider(type = InputBuilder.class, method = "buildUpdateInputList")
    int inputForm(InputDTO inputDTO);

    // 거래 명세서
    @SelectProvider(type = InputBuilder.class,method = "buildSelectTransactionList")
    List<TransactionDTO> transactionList();

    // 거래명세서 수정
    @UpdateProvider(type = InputBuilder.class, method = "buildUpdateTransactionList")
    int transactionForm(TransactionDTO transactionDTO);

    // 구매발주서 리스트
    @Select("select `order`.date, `order`.leadtime,`order`.status,`order`.value,item.name,item.price,item.unit from `order`" +
            " join item on item.item_id = `order`.item_id")
    List<OrderDTO> orderList();

    // 구매발주서 확인
    @Update("update `order` set status = #{status}  where order_code = #{order_code}")
    int orderForm(Map<String,Object> map);

    // 검수
    //    update문 where status = 100형식으로 다가가야할듯
    @UpdateProvider(type = InputBuilder.class, method = "buildUpdateInputStatus")
    int updateInputStatus(InputDTO inputDTO);
}
