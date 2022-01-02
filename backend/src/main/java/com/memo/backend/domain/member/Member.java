package com.memo.backend.domain.member;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "email",unique = true)
    private String email;

    private String password;

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
