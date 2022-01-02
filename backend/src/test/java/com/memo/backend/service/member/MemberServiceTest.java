package com.memo.backend.service.member;

import com.memo.backend.domain.member.MemberRepository;
import com.memo.backend.dto.member.MemberSaveDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

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
}