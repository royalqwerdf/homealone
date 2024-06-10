package com.elice.homealone.member.service;

import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.global.oauth.NaverProperties;
import com.elice.homealone.member.dto.TokenDto;
import com.elice.homealone.member.dto.response.KakaoUserResponse;
import com.elice.homealone.member.dto.response.NaverTokenResponse;
import com.elice.homealone.member.dto.response.NaverUserResponse;
import com.elice.homealone.member.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final NaverProperties naverProperties;
    private final AuthService authService;

    public KakaoUserResponse getKakaoUserInfo(String kakaoAcessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", kakaoAcessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoUserResponse kakaoUserDto = null;
        try {
            kakaoUserDto = objectMapper.readValue(response.getBody(), KakaoUserResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kakaoUserSignup(kakaoUserDto);
        return kakaoUserDto;
    }

    public void kakaoUserSignup(KakaoUserResponse kakaoUserDto) {
        try {
            if (!authService.isEmailDuplicate(kakaoUserDto.getKakao_account().getEmail())) {
                authService.signUp(kakaoUserDto.toSignupRequestDto());
            }
        } catch (HomealoneException e) {}
    }

    public TokenDto signupOrLogin(Member member, HttpServletResponse httpServletResponse) {
        //회원가입
        try{
            if(!authService.isEmailDuplicate(member.getEmail())){
                authService.signUp(member.toSignupRequestDto());
            }
        }catch (HomealoneException e) {}
        //로그인
        TokenDto tokenDto = authService.login(member.toLoginRequestDto(), httpServletResponse);
        return tokenDto;
    }

    public Member toEntityUser(String code) {
        String accessToken = toRequestAccessToken(code);
        NaverUserResponse.NaverUserDetail profile = toRequestProfile(accessToken);

        return Member.builder()
                .name(profile.getNickname())
                .email(profile.getEmail())
                .imageUrl(profile.getProfileImage())
                .password(profile.getId())
                .build();
    }

    //Code를 통해 accessToken 획득
    private String toRequestAccessToken(String code) {
        ResponseEntity<NaverTokenResponse> response =
                restTemplate.exchange(naverProperties.getRequestURL(code), HttpMethod.GET, null, NaverTokenResponse.class);
        // Validate를 만드는 것을 추천
        return response.getBody().getAccessToken();
    }

    //accessToken을 통해 유저정보 획득
    private NaverUserResponse.NaverUserDetail toRequestProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<NaverUserResponse> response =
                restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.GET, request, NaverUserResponse.class);
        return response.getBody().getNaverUserDetail();
    }


//        public OAuthTokenDto getAccessToken(String code) {
//            RestTemplate rt = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            params.add("grant_type", "authorization_code");
//            params.add("client_id", clientId);
//            params.add("redirect_uri", redirectUri);
//            params.add("code", code);
//
//            HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
//
//            ResponseEntity<String> response = rt.exchange(
//                    "https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
//                    HttpMethod.POST, // 요청할 방식
//                    kakaoTokenRequest, // 요청할 때 보낼 데이터
//                    String.class // 요청 시 반환되는 데이터 타입
//            );
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            OAuthTokenDto oAuthTokenDTO = null;
//            try {
//                oAuthTokenDTO = objectMapper.readValue(response.getBody(), OAuthTokenDto.class);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//            return oAuthTokenDTO;
//        }
}
