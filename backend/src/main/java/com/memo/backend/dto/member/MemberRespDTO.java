package com.memo.backend.dto.member;

import com.memo.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRespDTO {
    private String email;

    public static MemberRespDTO of(Member member) {
        return new MemberRespDTO(member.getEmail());
    }
}
