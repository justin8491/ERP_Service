package com.erp.mes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ShipmentDTO {
    private int shipId;        // 출고 ID
    private int stkId;         // 재고 ID
    private LocalDate reqDate; // 요청 날짜
    private int reqQty;        // 요청 수량
    private int qty;           // 실제 출고 수량
    private String status;     // 출고 상태
    private String loc;        // 출고 위치
}
