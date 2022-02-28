package com.memo.backend.config;

import com.memo.backend.domain.Authority.Authority;
import com.memo.backend.domain.Authority.AuthorityRepository;
import com.memo.backend.domain.Authority.MemberAuth;
import com.memo.backend.domain.member.Member;
import com.memo.backend.domain.member.MemberRepository;
import com.memo.backend.dto.member.MemberReqDTO;
import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.service.auth.AuthService;
import com.memo.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * initDataBaseForLocal 설명 : 로컬에서 테스트용으로 사용하기위해 데이터를 넣어 두기 위한 코드
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/02/28
**/
@Profile("local") // local 용
@Component
@RequiredArgsConstructor
public class initDataBaseForLocal {

    private final initDataBaseForLocalService initDataBaseForLocalService;

    @PostConstruct
    private void init() {
        this.initDataBaseForLocalService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class initDataBaseForLocalService {
        private final AuthService authService;
        private final MemberRepository memberRepository;
        private final AuthorityRepository authorityRepository;


        @Transactional
        public void init() {

            authorityRepository.save(new Authority(MemberAuth.ROLE_ADMIN));
            authorityRepository.save(new Authority(MemberAuth.ROLE_USER));


            authService.signup(new MemberReqDTO(
                    "admin@admin.com",
                    "1234",
                    "admin1"
            ));

            authService.signup(new MemberReqDTO(
                    "user@user.com",
                    "1234",
                    "user1"
            ));

            Member admin = memberRepository.findByEmail("admin@admin.com").get();
            Member user = memberRepository.findByEmail("user@user.com").get();

            admin.addAuthority(authorityRepository.findByAuthorityName(MemberAuth.ROLE_ADMIN).get());
            admin.activate(true);
            user.activate(true);
        }
    }
}
