package com.memo.backend.restcontroller.member;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.dto.member.MemberSaveDTO;
import com.memo.backend.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("멤버 조회 및 등록을 컨트롤러를 통해 하기")
    public void test1() throws Exception {
        String uri = "/api/member/v1";
        MemberSaveDTO req = new MemberSaveDTO();
        req.setEmail("hello@gmail.com");
        req.setPassword("123123");
        req.setUsername("HLEOO");

        MvcResult mvcResult = mockMvc.perform(
                        post(uri)
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();

        Long id = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Long.class);

        assertEquals(id,1L);


        MvcResult mvcResult2 = mockMvc.perform(
                get(uri + "/1").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print()).andReturn();

        MemberRespDTO memberRespDTO = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), MemberRespDTO.class);

        assertEquals(memberRespDTO.getEmail(),"hello@gmail.com");


    }

    @Test
    @DisplayName("모든 멤버 조회")
    public void test2() throws Exception{
        // before data
        MemberSaveDTO member = new MemberSaveDTO("user1","abc.com","1234");
        MemberSaveDTO member2 = new MemberSaveDTO("user2","abc2.com","1321");

        memberService.saveMember(member2);
        memberService.saveMember(member);



        String uri = "/api/members/v1";
        MvcResult mvcResult = mockMvc.perform(get(uri)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andReturn();

        List<MemberRespDTO> ret
                = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<MemberRespDTO>>() {
        });

        assertEquals(ret.size(),2);


    }

}