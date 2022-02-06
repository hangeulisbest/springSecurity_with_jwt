package com.memo.backend.restcontroller.login;

import com.memo.backend.commoncode.SessionConst;
import com.memo.backend.domain.member.Member;
import com.memo.backend.dto.login.LoginReqDTO;
import com.memo.backend.exceptionhandler.BizException;
import com.memo.backend.exceptionhandler.MemberExceptionType;
import com.memo.backend.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


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
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;

    /**
     *  설명 : Session 을 이용한 로그인
     * @author jowonjun
     * @version 1.0.0
     * 작성일 : 2022/01/06
    **/
    @PostMapping("/login/v1")
    public String login(@Valid @RequestBody LoginReqDTO loginReqDTO,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) throws BizException{
        Optional<Member> find = loginService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());
        if(find.isEmpty()) throw new BizException(MemberExceptionType.WRONG_PASSWORD);
        // session 생성 및 가져오기
        // session 은 메모리를 사용한다
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,find.get().getMemberId());

        return "login success next path is = " + redirectURL;
    }

    @GetMapping("/logout/v1")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
            return "로그아웃 성공";
        }
        return "세션정보가 없습니다.";
    }


}
