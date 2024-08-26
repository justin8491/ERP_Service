package com.erp.mes.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {

    private int m_code;
    private String id;
    private String pwd;
    private String name;
    private String phone_number;
    private String email;
    private String dept_name;
    private int auth;
    private int status;

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
