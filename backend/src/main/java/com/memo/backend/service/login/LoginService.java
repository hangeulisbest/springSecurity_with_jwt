package com.memo.backend.service.login;

import com.memo.backend.domain.member.Member;
import com.memo.backend.domain.member.MemberRepository;
import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * LoginService 설명 : 멤버 로그인 처리하는 서비스
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/03
**/
@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final MemberRepository memberRepository;

    @Transactional
    public Optional<Member> login(String email,String password){

        return memberRepository
                .findByEmail(email)
                .filter(m -> m.getPassword().equals(password));
    }
}
