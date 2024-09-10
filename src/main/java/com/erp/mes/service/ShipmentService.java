package com.erp.mes.service;

import com.erp.mes.dto.ShipmentDTO;
import com.erp.mes.mapper.ShipmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentMapper shipmentMapper;

    // 출고 요청 생성
    public int createShipment(ShipmentDTO shipmentDTO) {
        return shipmentMapper.insertShipment(shipmentDTO);
    }

    // 출고 목록 조회 (필터링 가능)
    public List<ShipmentDTO> getShipmentList(Map<String, Object> params) {
        return shipmentMapper.selectShipmentList(params);
    }

    // 출고 완료 처리
    public int completeShipment(int shipId) {
        return shipmentMapper.updateShipmentStatusToCompleted(shipId);
    }

    // 출고 요청 취소
    public int cancelShipment(int shipId) {
        return shipmentMapper.cancelShipment(shipId);
    }

    // 출고 상태 업데이트 (승인, 진행 중 등)
    public int updateShipmentStatus(int shipId, String status) {
        return shipmentMapper.updateShipmentStatus(shipId, status);
    }

    // 출고 시 재고 차감
    public int updateStockAfterShipment(int stkId, int reqQty) {
        return shipmentMapper.updateStockAfterShipment(stkId, reqQty);
    }
}
