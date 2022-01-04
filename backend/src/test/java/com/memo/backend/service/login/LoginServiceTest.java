package com.memo.backend.service.login;

import com.memo.backend.domain.member.Member;
import com.memo.backend.dto.member.MemberSaveDTO;
import com.memo.backend.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

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


        Optional<Member> login = loginService.login("wj100213@gmail.com", "1234");
        assertEquals(login.get().getId(),1L);
    }


    @Test
    @DisplayName("로그인이 실패하는 경우")
    @Transactional
    public void test2(){
        MemberSaveDTO dto = new MemberSaveDTO();
        dto.setEmail("wj100213@gmail.com");
        dto.setPassword("1234");
        memberService.saveMember(dto);
        em.flush();
        em.clear();


        Optional<Member> login = loginService.login("wj100213@gmail.com", "1234444");
        assertTrue(login.isEmpty());

    }
}