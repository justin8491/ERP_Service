package com.erp.mes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class InspectionDTO {
    private int ins_id;

    private Date date;
    private String notice;
    private int status;
}
