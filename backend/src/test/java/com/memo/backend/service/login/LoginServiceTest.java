package com.memo.backend.service.login;

import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.dto.member.MemberSaveDTO;
import com.memo.backend.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceTest {
    @Autowired
    LoginService loginService;

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("로그인이 정상적으로 되는경우")
    @Transactional
    public void test1(){
        MemberSaveDTO dto = new MemberSaveDTO();
        dto.setEmail("wj100213@gmail.com");
        dto.setPassword("1234");
        memberService.saveMember(dto);
        em.flush();
        em.clear();


        MemberRespDTO successLogin = loginService.login("wj100213@gmail.com","1234");
        assertEquals(successLogin.getId(),1L);
    }
}