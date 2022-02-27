package com.memo.backend.service.member;

import com.memo.backend.domain.Authority.Authority;
import com.memo.backend.domain.Authority.AuthorityRepository;
import com.memo.backend.domain.Authority.MemberAuth;
import com.memo.backend.domain.member.Member;
import com.memo.backend.domain.member.MemberRepository;
import com.memo.backend.dto.member.MemberReqDTO;
import com.memo.backend.dto.member.MemberUpdateDTO;
import com.memo.backend.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    AuthService authService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;
    /**
     * 각 테스트 실행전에 실행
     */
    @BeforeEach
    public void beforeEach() {
        authorityRepository.save(new Authority(MemberAuth.ROLE_USER));
        authorityRepository.save(new Authority(MemberAuth.ROLE_ADMIN));

        MemberReqDTO dto = new MemberReqDTO();
        dto.setUsername("normalUser");
        dto.setEmail("normalUser@normalUser.com");
        dto.setPassword("1234");

        authService.signup(dto);
    }

    @Test
    @DisplayName("멤버의 정보를 바꿔본다.")
    @Transactional
    public void memberAuthChange() {
        //given
        MemberUpdateDTO updateDTO = new MemberUpdateDTO();
        updateDTO.setEmail("normalUser@normalUser.com");
        updateDTO.setUsername("updateName");
        updateDTO.setPassword("54321");
        updateDTO.setAuthorities(Arrays.asList("ROLE_ADMIN","ROLE_USER"));

        Member find = memberRepository.findByEmail("normalUser@normalUser.com").get();

        // when
        find.updateMember(updateDTO,passwordEncoder);

        em.flush();
        em.clear();

        Member find2 = memberRepository.findByEmail(updateDTO.getEmail()).get();

        // then
        assertTrue(passwordEncoder.matches(updateDTO.getPassword(), find2.getPassword()));
        assertEquals(find2.getUsername(),updateDTO.getUsername());
        assertEquals(2,find2.getAuthorities().size());
    }
}