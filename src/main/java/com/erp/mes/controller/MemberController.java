package com.erp.mes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MemberController {

//    @GetMapping(value = "/home")
//    public String home(){
//
//        return "home";
//    }


    /**
     * 로그인 창 이동
     * @return
     */
    @GetMapping(value = "login")
    public String loginForm() {
        return "member/login";
    }

}
