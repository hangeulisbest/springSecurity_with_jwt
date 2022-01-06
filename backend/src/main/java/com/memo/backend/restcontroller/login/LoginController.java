package com.memo.backend.restcontroller.login;

import com.memo.backend.commoncode.SessionConst;
import com.memo.backend.domain.member.Member;
import com.memo.backend.dto.login.LoginReqDTO;
import com.memo.backend.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * LoginController 설명 : login
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/05
**/
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {
    private final LoginService loginService;

    /**
     *  설명 : Session 을 이용한 로그인
     * @author jowonjun
     * @version 1.0.0
     * 작성일 : 2022/01/06
    **/
    @PostMapping("/v1")
    public String login(@Valid @RequestBody LoginReqDTO loginReqDTO, HttpServletRequest request) throws Exception{
        Optional<Member> find = loginService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());
        if(find.isEmpty()){
            throw new NoSuchElementException("유저 정보가 없거나 비밀번호가 틀립니다.");
        }
        // session 생성 및 가져오기
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,find.get());

        return String.valueOf(find.get().getId());
    }


}
