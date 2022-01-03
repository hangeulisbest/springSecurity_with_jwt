package com.memo.backend.restcontroller.member;

import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.dto.member.MemberSaveDTO;
import com.memo.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * MemberController 설명 :
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/03
**/
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/v1")
    public Long saveMember(@RequestBody @Valid MemberSaveDTO saveDTO){
        return memberService.saveMember(saveDTO);
    }

    @GetMapping("/v1/{id}")
    public MemberRespDTO findById(@PathVariable Long id){
        return memberService.findById(id);
    }

}
