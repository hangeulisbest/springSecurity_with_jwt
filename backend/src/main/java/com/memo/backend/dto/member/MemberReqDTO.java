package com.memo.backend.dto.member;

import com.memo.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .activated(false)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email,password);
    }

}
