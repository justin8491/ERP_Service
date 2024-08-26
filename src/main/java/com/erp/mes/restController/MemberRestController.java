package com.erp.mes.restController;


import com.erp.mes.dto.MemberDTO;
import com.erp.mes.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/member/*")
public class MemberRestController {

    @Autowired
    MemberService memberService;



    @PostMapping(value = "findAll")
    public Map<String, Object> findAll(){
        Map<String, Object> map = new HashMap<>();
        try {
            List<MemberDTO> memberList = memberService.findAll();
            map.put("memberList",memberList);
            map.put("msg","회원전부 찾기 성공");
        }catch (Exception e){
            map.put("msg","회원전부 찾기 실패");
        }

        return map;
    }

    @PostMapping(value = "find")
    public Map<String, Object> find(@RequestBody MemberDTO memberDTO){
        Map<String, Object> map = new HashMap<>();
        try {
            memberDTO = memberService.find(memberDTO);
            map.put("memberDTO",memberDTO);
            map.put("msg","회원 찾기 성공");
        }catch (Exception e){
            map.put("msg","회원 찾기 실패");
        }


        return map;
    }

    @PostMapping(value = "login")
    public Map<String, Object> login(@RequestBody MemberDTO memberDTO) {
        Map<String, Object> map = new HashMap<>();
        try {

            MemberDTO member = memberService.login(memberDTO);
            if (member == null) {
                map.put("msg", "아이디 또는 패스워드가 틀립니다.");
                map.put("loc", "member/login");
            } else {
                map.put("member", member);
                map.put("loc", "home");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "아이디 또는 패스워드가 틀립니다.");
            map.put("loc", "member/login");
        }
        return map;
    }

    @PostMapping(value = "join")
    public Map<String, Object> join(@RequestBody MemberDTO memberDTO) {
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println(memberDTO);
            int status = memberService.join(memberDTO);
            map.put("status",status);
            map.put("msg", "회원가입 완료");
            map.put("loc", "member/login");
        } catch (Exception e) {
            map.put("msg", "회원가입 실패");
            map.put("loc", "member/login");
            map.put("error",e.getMessage());
        }
        return map;
    }

    @PutMapping(value = "update")
    public Map<String, Object> update(@RequestBody MemberDTO memberDTO) {
        Map<String, Object> map = new HashMap<>();
        try {
            int status = memberService.update(memberDTO);
            map.put("status",status);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "회원 수정 실패");
        }
        return map;
    }

    @PutMapping(value = "delete")
    public Map<String, Object> delete(@RequestBody MemberDTO memberDTO){
        Map<String, Object> map = new HashMap<>();
        System.out.println(memberDTO);
        try {
            int status = memberService.delete(memberDTO);
            map.put("status",status);
            map.put("msg", "회원 삭제 성공");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "회원 삭제 실패");
        }
        return map;
    }
}
