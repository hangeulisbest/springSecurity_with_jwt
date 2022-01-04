package com.memo.backend.restcontroller.login;

import com.memo.backend.domain.member.Member;
import com.memo.backend.dto.login.LoginReqDTO;
import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.dto.member.MemberSaveDTO;
import com.memo.backend.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final ModelMapper modelMapper;

    @PostMapping("/v1")
    public MemberRespDTO login(@Valid @RequestBody LoginReqDTO loginReqDTO) throws Exception{
        Optional<Member> find = loginService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());
        if(find.isEmpty()){
            throw new NoSuchElementException("유저 정보가 없거나 비밀번호가 틀립니다.");
        }
        return modelMapper.map(find.get(),MemberRespDTO.class);
    }
}
