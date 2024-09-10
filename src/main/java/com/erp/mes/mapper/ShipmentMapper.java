package com.erp.mes.mapper;

import com.erp.mes.dto.ShipmentDTO;
import com.erp.mes.sqlBuilder.ShipmentBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShipmentMapper {

    // 출고 요청 생성
    @InsertProvider(type = ShipmentBuilder.class, method = "insertShipment")
    int insertShipment(ShipmentDTO shipmentDTO);

    // 출고 목록 조회
    @SelectProvider(type = ShipmentBuilder.class, method = "selectShipmentList")
    List<ShipmentDTO> selectShipmentList(Map<String, Object> params);

    // 출고 완료 처리
    @UpdateProvider(type = ShipmentBuilder.class, method = "updateShipmentStatusToCompleted")
    int updateShipmentStatusToCompleted(@Param("shipId") int shipId);

    // 출고 요청 취소
    @UpdateProvider(type = ShipmentBuilder.class, method = "cancelShipment")
    int cancelShipment(@Param("shipId") int shipId);

    // 출고 요청 상태 업데이트
    @UpdateProvider(type = ShipmentBuilder.class, method = "updateShipmentStatus")
    int updateShipmentStatus(@Param("shipId") int shipId, @Param("status") String status);

    // 출고 요청 시 재고 차감
    @UpdateProvider(type = ShipmentBuilder.class, method = "updateStockAfterShipment")
    int updateStockAfterShipment(@Param("stkId") int stkId, @Param("reqQty") int reqQty);
}
