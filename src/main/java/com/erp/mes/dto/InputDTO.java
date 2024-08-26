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
public class InputDTO {
    // 입고
    private int input_id; // 기본키
    private boolean type; // 0과 1 미/확정
    private LocalDate expDAte; // 출고예정일
    private LocalDate recDate; // 받은날
    // 저장창고
    private String invenName; // 저장창고이름
    // 물품
    private String itemName; // 물품이름
    private int price; // 물품가격
    private String unit; // 물품 견적
    // 검수
    private String status; // 검수 현황상태
}
