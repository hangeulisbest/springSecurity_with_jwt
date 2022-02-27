package com.memo.backend.service.auth;

import com.memo.backend.domain.Authority.Authority;
import com.memo.backend.domain.Authority.AuthorityRepository;
import com.memo.backend.domain.Authority.MemberAuth;
import com.memo.backend.domain.member.Member;
import com.memo.backend.domain.member.MemberRepository;
import com.memo.backend.dto.member.MemberReqDTO;
import com.memo.backend.exceptionhandler.BizException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    AuthService authService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthorityRepository authorityRepository;

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

    @DisplayName("회원가입이 정상적으로 된다")
    @Test
    @Transactional
    public void signup(){
        MemberReqDTO dto = new MemberReqDTO();
        dto.setUsername("user1");
        dto.setEmail("test@test.com");
        dto.setPassword("54321");

        authService.signup(dto);

        // 영속성 컨텍스트 플러쉬
        em.flush();
        em.clear();

        Optional<Member> ret = memberRepository.findByEmail("test@test.com");
        assertEquals(ret.get().getUsername(),"user1");
    }

    @DisplayName("이미 존재하는 회원인 경우 회원가입 불가")
    @Test
    @Transactional
    public void signupDuplicateMember() {
        MemberReqDTO dto = new MemberReqDTO();
        dto.setUsername("normalUser");
        dto.setEmail("normalUser@normalUser.com");
        dto.setPassword("1234");

        BizException bizException = assertThrows(BizException.class, () -> {
            authService.signup(dto);
        });

        assertEquals(bizException.getBaseExceptionType().getErrorCode(),"DUPLICATE_USER");
    }
}