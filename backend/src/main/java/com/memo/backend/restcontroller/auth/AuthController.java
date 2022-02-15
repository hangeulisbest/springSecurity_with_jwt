package com.memo.backend.restcontroller.auth;

import com.memo.backend.dto.jwt.TokenDTO;
import com.memo.backend.dto.jwt.TokenReqDTO;
import com.memo.backend.dto.member.MemberReqDTO;
import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * AuthController 설명 : auth controller
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/02/14
**/
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;

        @PostMapping("/signup")
        public MemberRespDTO signup(@RequestBody MemberReqDTO memberRequestDto) {
            log.debug("memberRequestDto = {}",memberRequestDto);
            return authService.signup(memberRequestDto);
        }

        @PostMapping("/login")
        public TokenDTO login(@RequestBody MemberReqDTO memberRequestDto) {
            return authService.login(memberRequestDto);
        }

        @PostMapping("/reissue")
        public TokenDTO reissue(@RequestBody TokenReqDTO tokenRequestDto) {
            return authService.reissue(tokenRequestDto);
        }
}
