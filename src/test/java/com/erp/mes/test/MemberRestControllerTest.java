package com.erp.mes.test;

import com.erp.mes.dto.MemberDTO;
import com.erp.mes.restController.MemberRestController;
import com.erp.mes.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class MemberRestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberRestController memberRestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(memberRestController).build();
    }

//    @Test
//    public void testLogin_Success() throws Exception {
//        MemberDTO memberDTO = new MemberDTO();
//        memberDTO.setId("user1");
//        memberDTO.setPwd("password1");
//
//        when(memberService.login(any(MemberDTO.class))).thenReturn(memberDTO);
//
//        mockMvc.perform(post("/member/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("id", "user1")
//                        .param("pwd", "password1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.msg").value("회원가입 완료"))
//                .andExpect(jsonPath("$.loc").value("home"));
//
//        verify(memberService, times(1)).login(any(MemberDTO.class));
//    }

    @Test
    void join() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}