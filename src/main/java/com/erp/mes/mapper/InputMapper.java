package com.erp.mes.mapper;

import com.erp.mes.dto.InputDTO;
import com.erp.mes.dto.TransactionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InputMapper {

    // 입고리스트
    @Select("select inventory.name as invenName ,item.name as itemName,item.price,item.unit,input.rec_date from input" +
            " join inventory on inventory.inven_id = input.inven_id" +
            " join item on item.item_id = inventory.item_id")
    List<InputDTO> inputList();
    // 거래 명세서
    @Select("select transaction.date AS transDate,transaction.val,transaction.status,`order`.date AS orderDate, `order`.leadtime,item.name,item.unit from transaction" +
            " join `order`on `order`.order_code = transaction.refer_code" +
            " join item on `order`.item_id = item.item_id")
    List<TransactionDTO> transactionList();

}
