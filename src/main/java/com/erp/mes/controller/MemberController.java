package com.erp.mes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MemberController {

    @GetMapping(value = "member/login")
    public String loginForm() {
        return "member/login";
    }

    @GetMapping(value = "member/register")
    public String registerForm() {
        return "member/register";
    }

}
