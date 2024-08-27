package com.erp.mes.dao;

import com.erp.mes.dto.ItemDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemMapper {

    @Select("SELECT * FROM item WHERE item_id = #{id}")
    ItemDTO getItemById(int id); //상세조회


    @Select("SELECT * FROM item")
    List<ItemDTO> getAllItems(); //전체 목록


    @Insert("INSERT INTO item (type, name, spec, unit, price, create_date) " +
            "VALUES (#{type}, #{name}, #{spec}, #{unit}, #{price}, #{create_date})")
    @Options(
            useGeneratedKeys = true,
            keyProperty = "item_id"
    )
    int insert(ItemDTO item);
    //품목추가


    @Update("UPDATE item SET type=#{type}, name=#{name}, spec=#{spec}, unit=#{unit}, price=#{price}, create_date=#{create_date} " +
            "WHERE item_id=#{item_id}")
    int update(ItemDTO item);
    //업데이트


    @Delete("DELETE FROM item WHERE item_id = #{id}")
    int deleteItem(int id);
    // 삭제


    @Select("<script>" +
            "SELECT * FROM item WHERE 1=1" +
            "<if test='name != null and name != \"\"'> AND name LIKE CONCAT('%', #{name}, '%')</if>" +
            "<if test='type != null and type != \"\"'> AND type = #{type}</if>" +
            "<if test='minPrice != null'> AND price &gt;= #{minPrice}</if>" +
            "<if test='maxPrice != null'> AND price &lt;= #{maxPrice}</if>" +
            "</script>")
    List<ItemDTO> searchItems(@Param("name") String name,
                              @Param("type") String type,
                              @Param("minPrice") Double minPrice,
                              @Param("maxPrice") Double maxPrice);
    //상세검색

}



