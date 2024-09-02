package com.erp.mes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class OrderDTO {
    // 발주서
    private LocalDate date; // 발주일자
    private String status; // 발주상태
    private String value; // 발주금액
    private String orderCode; // 발주코드
    private String supId; // 회사 외래키
    private String planId; // 발주계획 외래키
}
