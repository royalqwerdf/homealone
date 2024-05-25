package com.elice.homealone.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}
