package com.memo.backend.dto.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenReqDTO {
    private String accessToken;
    private String refreshToken;
}
