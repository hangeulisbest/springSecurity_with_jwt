package com.memo.backend.restcontroller.member;

import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.dto.member.MemberSaveDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

    @Autowired
    TestRestTemplate restTemplate;


    @Test
    @DisplayName("멤버 조회 및 등록을 컨트롤러를 통해 하기")
    public void test1(){
        String uri = "/member/v1";
        MemberSaveDTO req = new MemberSaveDTO();
        req.setEmail("hello@gmail.com");
        req.setPassword("123123");
        Long finds = restTemplate.postForObject(uri, req, Long.class);

        assertEquals(finds,1L);

        uri += "/1";
        MemberRespDTO getObj = restTemplate.getForObject(uri, MemberRespDTO.class);
        assertEquals(getObj.getEmail(),"hello@gmail.com");
    }

}