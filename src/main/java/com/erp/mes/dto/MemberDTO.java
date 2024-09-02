package com.erp.mes.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {

    private int m_code; // 회원코드
    private String id; // 회원 ID
    private String pwd; // 회원 비밀번호
    private String name; // 회원 이름
    private String phone_number; // 핸드폰 번호
    private String email; // 이메일
    private String dept_name; // 부서 이름
    private int auth; // 회원 권한
    private int status; // 회원 상태

//    public MemberDTO(String id, String pwd, String name, String phone_number, String email, String dept_name, int auth, int status) {
//        this.id = id;
//        this.pwd = pwd;
//        this.name = name;
//        this.phone_number = phone_number;
//        this.email = email;
//        this.dept_name = dept_name;
//        this.auth = auth;
//        this.status = status;
//    }
}
