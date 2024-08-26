package com.erp.mes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class PlanDTO {
    // 조달 계획
    private int plan_id;
    private int qty;
    private Date date;
    private Date leadtime;
    private String status;

    // 품목
    private int item_id;
}
