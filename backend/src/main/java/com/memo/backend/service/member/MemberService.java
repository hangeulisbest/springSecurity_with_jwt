package com.memo.backend.service.member;

import com.memo.backend.domain.member.Member;
import com.memo.backend.domain.member.MemberRepository;
import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.dto.member.MemberSaveDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * MemberService 설명 : 멤버 등록 및 조회
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/02
**/
@Slf4j
@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long saveMember(MemberSaveDTO saveDTO){
        Member member = modelMapper.map(saveDTO, Member.class);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional(readOnly = true)
    public MemberRespDTO findById(Long id) throws IllegalArgumentException{
        Optional<Member> finds = memberRepository.findById(id);

        if(finds.isEmpty()) throw new IllegalArgumentException("아이디 [ " + id+" ] 에 해당하는 멤버가 없습니다.");

        return modelMapper.map(
                finds.get(),
                MemberRespDTO.class
        );
    }
}
