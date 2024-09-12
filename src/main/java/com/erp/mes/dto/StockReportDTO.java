package com.erp.mes.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class StockReportDTO {
    private int stk_id;        // 재고 ID
    private int rep_id;        // 리포트 ID
    private String period;     // 리포트 기간
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;         // 리포트 날짜
    private int item_id;       // 자재 ID
    private String itemName;   // 자재 이름
    private String itemCode;   // 자재 코드
    private BigDecimal totalValue;  // 총 재고 금액
    private int totalQty;      // 총 재고 수량
    private String location;   // 재고 위치
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate; // 유효 기간
    private int remainingQty;  // 잔여 수량
    private BigDecimal unitPrice; // 단가
}