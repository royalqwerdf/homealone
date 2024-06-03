package com.elice.homealone.member.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    public String accessToken;
    public String message;

}
