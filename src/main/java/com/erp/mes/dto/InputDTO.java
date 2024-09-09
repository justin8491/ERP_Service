package com.erp.mes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.util.Date;
>>>>>>> newjihye

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InputDTO {
<<<<<<< HEAD
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
=======
    private String keyword;
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
>>>>>>> newjihye
