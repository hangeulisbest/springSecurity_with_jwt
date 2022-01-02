package com.memo.backend.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberRespDTO {
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
