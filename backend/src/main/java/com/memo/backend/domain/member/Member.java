package com.memo.backend.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.memo.backend.domain.Authority.Authority;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Entity
public class Member {

    @JsonIgnore
    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "username",length = 50,nullable = false)
    private String username;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name="member_id",referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name",referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities;

}
