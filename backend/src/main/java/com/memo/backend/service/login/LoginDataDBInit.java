package com.memo.backend.service.login;

import com.memo.backend.dto.member.MemberSaveDTO;
import com.memo.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * LoginDataDBInit 설명 : 로컬환경에서만 로그인멤버를 임시로 넣어두는 클래스
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/04
**/

@Profile("local")
@Component
@RequiredArgsConstructor
public class LoginDataDBInit {

    private final DBInit dbInit;

    @PostConstruct
    public void init(){
        dbInit.init();
    }

    @Component
    @RequiredArgsConstructor
    static class DBInit{
        private final MemberService memberService;

        public void init(){
            MemberSaveDTO m1 = new MemberSaveDTO("wonjun","wj100213@gmail.com","1234");
            MemberSaveDTO m2 = new MemberSaveDTO("hello","lokshopi@naver.com","helloworld!");
            memberService.saveMember(m1);
            memberService.saveMember(m2);
        }
    }
}
