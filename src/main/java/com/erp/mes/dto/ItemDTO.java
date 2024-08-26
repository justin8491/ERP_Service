package com.erp.mes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class ItemDTO {
    private int item_id;
    private String type;
    private String name;
    private String spec;
    private String unit;
    private long price;
}
