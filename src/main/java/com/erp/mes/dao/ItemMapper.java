package com.erp.mes.dao;

import com.erp.mes.dto.ItemDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemMapper {

    @Select("SELECT * FROM item WHERE item_id = #{id}")
    ItemDTO getItemById(int id);

    @Select("SELECT * FROM item")
    List<ItemDTO> getAllItems();

    @Insert("INSERT INTO item (type, name, spec, unit, price, create_date) " +
            "VALUES (#{type}, #{name}, #{spec}, #{unit}, #{price}, #{create_date})")
    @Options(useGeneratedKeys = true, keyProperty = "item_id")
    void insert(ItemDTO item);
    // 품목 추가


    @Update("UPDATE item SET type=#{type}, name=#{name}, spec=#{spec}, unit=#{unit}, price=#{price}, create_date=#{create_date} " +
            "WHERE item_id=#{item_id}")
    void update(ItemDTO item);
    // 품목 수정

    @Delete("DELETE FROM item WHERE item_id = #{id}")
    void deleteItem(int id);

    @Select("<script>" +
            "SELECT * FROM item WHERE 1=1" +
            "<if test='name != null and name != \"\"'> AND name LIKE CONCAT('%', #{name}, '%')</if>" +
            "<if test='type != null and type != \"\"'> AND type = #{type}</if>" +
            "<if test='minPrice != null'> AND price &gt;= #{minPrice}</if>" +
            "<if test='maxPrice != null'> AND price &lt;= #{maxPrice}</if>" +
            "</script>")
    List<ItemDTO> searchItems(
            @Param("name") String name,
            @Param("type") String type,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
}



