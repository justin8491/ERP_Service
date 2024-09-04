package com.erp.mes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InputDTO {
    // 입고
    private int inputId; // 기본키
    private boolean type; // 0과 1 미/확정
    private LocalDate expDate; // 출고예정일
    private LocalDate recDate; // 받은날
    private int tranId; // 거래내역 외래키

    // 거래내역

    // 협력사
    private String supName; // 협력사이름

    // 창고
    private String invenName; // 창고이름

    // 아이템
    private String itemName; // 물품이름

    // 조달계획
    private int qty; // 수량

    // 검수
    private String status; // 검수상태
}
