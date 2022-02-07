package com.memo.backend.restcontroller.member;

import com.memo.backend.argumentresolver.Login;
import com.memo.backend.commoncode.SessionConst;
import com.memo.backend.domain.member.Member;
import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.dto.member.MemberSaveDTO;
import com.memo.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * MemberController 설명 :
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/03
**/
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public MemberRespDTO getMyInfo() {
        return memberService.getMyInfo();
    }

    @GetMapping("/{email}")
    public MemberRespDTO getMemberInfo(@PathVariable String email) {
        return memberService.getMemberInfo(email);
    }

}
