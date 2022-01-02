package com.memo.backend.service.member;

import com.memo.backend.domain.member.MemberRepository;
import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.dto.member.MemberSaveDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("멤버 서비스에서 멤버가 저장된다")
    @Test
    @Transactional
    public void test1(){
        MemberSaveDTO dto = new MemberSaveDTO();
        dto.setEmail("wj100213@gmail.com");
        dto.setPassword("1234");

        MemberSaveDTO dto2 = new MemberSaveDTO();
        dto2.setPassword("4321");
        dto2.setEmail("notemail@naver.com");
        memberService.saveMember(dto);
        memberService.saveMember(dto2);

        assertEquals(memberService.findById(2L).getPassword(),"4321");
    }

    @DisplayName("빈값의 이메일이나 패스워드를 저장할시 오류가 난다")
    @Test
    @Transactional
    public void test2(){
        MemberSaveDTO dto = new MemberSaveDTO();
        memberService.saveMember(dto);
        assertThrows(PersistenceException.class,()->{
            // flush 할때 오류가 발생함
            entityManager.flush();
        });
    }

    @DisplayName("이메일로 찾아오기")
    @Test
    @Transactional
    public void test3(){
        MemberSaveDTO dto = new MemberSaveDTO();
        dto.setPassword("1234");
        dto.setEmail("wj100213@gmail.com");
        memberService.saveMember(dto);
        entityManager.flush();
        entityManager.clear();

        MemberRespDTO ret = memberService.findByEmail("wj100213@gmail.com");
        assertEquals(ret.getId(),1L);
        assertEquals(ret.getEmail(),"wj100213@gmail.com");
    }

}