package com.erp.mes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputController {
    @GetMapping("input/inputList")
    public String inputList() {
        return "ok";
    }
}
