package com.elice.homealone.member.dto;

import com.elice.homealone.member.dto.request.LoginRequestDto;
import com.elice.homealone.member.dto.request.SignupRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserDto {
    private String id;
    private KakaoAccount kakao_account;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {
        private Profile profile;
        private String email;
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
        }
    }

    public SignupRequestDto toSignupRequestDto() {
        return SignupRequestDto.builder()
                .name(this.kakao_account.profile.getNickname())
                .email(this.kakao_account.getEmail())
                .password(this.getId())
                .build();
    }

    public LoginRequestDto toLoginRequestDto() {
        LoginRequestDto loginRequestDTO = new LoginRequestDto();
        loginRequestDTO.setEmail(this.kakao_account.getEmail());
        loginRequestDTO.setPassword(this.getId());
        return loginRequestDTO;
    }

}