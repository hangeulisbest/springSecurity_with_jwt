package com.memo.backend.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class MemberSaveDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
