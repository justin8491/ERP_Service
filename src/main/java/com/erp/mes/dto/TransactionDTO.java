package com.erp.mes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
    private String transDate; // 거래날짜
    private int value; // 거래내역
    private String status; // 거래현황
    // 발주서
    private LocalDate orderDate; // 발주일자
    private LocalDate leadTime; // 발주납기일
}
