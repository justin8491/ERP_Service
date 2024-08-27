package com.erp.mes.dao;

import com.erp.mes.dto.StockDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StockMapper {

    // ID로 재고 조회
    @Select("SELECT * FROM stock WHERE stk_id = #{stk_id}")
    StockDTO getStockById(int stk_id);

    // 모든 재고 조회
    @Select("SELECT * FROM stock")
    List<StockDTO> getAllStocks();

    // 재고 삽입
    @Insert("INSERT INTO stock (qty, loc, value, status, in_date, exp_date, cons_qty, cons_date, cons_loc) " +
            "VALUES (#{qty}, #{loc}, #{value}, #{status}, #{in_date}, #{exp_date}, #{cons_qty}, #{cons_date}, #{cons_loc})")
    @Options(useGeneratedKeys = true, keyProperty = "stk_id")
    int insertStock(StockDTO stock);

    // 재고 업데이트
    @Update("UPDATE stock SET qty=#{qty}, loc=#{loc}, value=#{value}, status=#{status}, " +
            "in_date=#{in_date}, exp_date=#{exp_date}, cons_qty=#{cons_qty}, cons_date=#{cons_date}, cons_loc=#{cons_loc} " +
            "WHERE stk_id=#{stk_id}")
    int updateStock(StockDTO stock);

    // 재고 삭제
    @Delete("DELETE FROM stock WHERE stk_id = #{stk_id}")
    int deleteStock(int stk_id);
}
