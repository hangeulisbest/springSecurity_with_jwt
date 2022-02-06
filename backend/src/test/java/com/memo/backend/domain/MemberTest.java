package com.memo.backend.domain;

import com.memo.backend.domain.member.Member;
import com.memo.backend.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


import java.util.Optional;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("멤버 등록 조회 테스트")
    @Test
    @Transactional
    public void test1(){
//        Member member = Member.builder()
//                .email("wj100213@gmail.com")
//                .password("1234")
//                .build();
//
//        memberRepository.save(member);
//
//        Optional<Member> finds = memberRepository.findById(member.getId());
//
//        assertThat(finds.get().getId(),is(equalTo(1L)));
//        assertThat(finds.get(),is(equalTo(member)));
    }
}