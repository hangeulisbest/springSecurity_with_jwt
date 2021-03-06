package com.memo.backend.dto.member;

import com.memo.backend.domain.Authority.Authority;
import com.memo.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * MemberReqDTO 설명 :
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/02/08
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberReqDTO {
    private String email;
    private String password;
    private String username;

    public Member toMember(PasswordEncoder passwordEncoder,Set<Authority> authorities) {
        return Member.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .activated(false)
                .authorities(authorities)
                .build();
    }

}
