package com.elice.homealone.module.member.dto.response;

import com.elice.homealone.module.member.dto.request.LoginRequestDto;
import com.elice.homealone.module.member.dto.request.SignupRequestDto;
import com.elice.homealone.module.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserResponse {
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

    public Member toMember(){
        return Member.builder()
                .name(this.kakao_account.profile.getNickname())
                .email(this.kakao_account.getEmail())
                .password(this.getId())
                .build();
    }

}