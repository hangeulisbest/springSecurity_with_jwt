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

//
//    @PostMapping("/v1")
//    public Long saveMember(@RequestBody @Valid MemberSaveDTO saveDTO){
//        return memberService.saveMember(saveDTO);
//    }
//
//    @GetMapping("/v1/{id}")
//    public MemberRespDTO findById(@PathVariable Long id){
//        return memberService.findById(id);
//    }
//
//    @GetMapping("/v1")
//    public MemberRespDTO findBySession(
//            @SessionAttribute(value = SessionConst.LOGIN_MEMBER,required = false) Long id){
//        log.debug("find-session => id={}",id);
//        if(id != null) return memberService.findById(id);
//        else return null;
//    }
//
//    @GetMapping("/sessiontest")
//    public String sessionTest(@Login Long memberId){
//        if(memberId == null) return "세션이 없습니다.";
//        return memberService.findById(memberId).getEmail() + " 님 반갑습니다.";
//    }
//
//    @GetMapping("/v1")
//    public List<MemberRespDTO> findAll(){
//        return memberService.findAll();
//    }
}
