package com.erp.mes.mapper;

import com.erp.mes.dto.InputDTO;
import com.erp.mes.dto.OrderDTO;
import com.erp.mes.dto.TransactionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface InputMapper {

    // 입고리스트
    @Select("select inventory.name as invenName ,item.name as itemName,item.price,item.unit,input.rec_date from input" +
            " join inventory on inventory.inven_id = input.inven_id" +
            " join item on item.item_id = inventory.item_id")
    List<InputDTO> inputList();
    // 입고확정
    @Update("update input inner join inspection on inspection.ins_id = input.input_id" +
            " set type = 1 where inspection.status = 100")
    int inputForm();
    // 거래 명세서
    @Select("select transaction.date AS transDate,transaction.val,transaction.status,`order`.date AS orderDate, `order`.leadtime,item.name,item.unit from transaction" +
            " join `order`on `order`.order_code = transaction.refer_code" +
            " join item on `order`.item_id = item.item_id")
    List<TransactionDTO> transactionList();
    // 거래명세서 수정
    @Update("update transaction " +
            "inner join `order` on `order`.order_Code = transaction.refer_code" +
            " inner join item on `order`.item_id = item.item_id" +
            " set item.unit = #{unit},transaction.val = #{val}, transaction.status = #{status}" +
            " where transaction.refer_code = #{refer_code}")
    int transactionForm(Map<String,Object> map);
    // 구매발주서 리스트
    @Select("select `order`.date, `order`.leadtime,`order`.status,`order`.value,item.name,item.price,item.unit from `order`" +
            " join item on item.item_id = `order`.item_id")
    List<OrderDTO> orderList();
    // 구매발주서 확인
    @Update("update `order` set status = #{status}  where order_code = #{order_code}")
    int orderForm(Map<String,Object> map);

}
