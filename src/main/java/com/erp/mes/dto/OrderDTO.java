package com.erp.mes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class OrderDTO {
    // 발주서
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date; // 발주일자
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate leadtime;// 발주납기일
    private String status; // 발주상태
    private String value; // 발주금액
    private String orderCode; // 발주코드

    // 물픔
    private String name; // 물품이름
    private int price; // 가격
    private String unit; // 단위
}
