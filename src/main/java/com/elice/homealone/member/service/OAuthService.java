package com.elice.homealone.member.service;

import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.KakaoUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AuthService authService;

    @Value("${kakao.client.id}")
    private String clientId;
    @Value("${kakdo.redirect.uri}")
    private String redirectUri;

        public KakaoUserDto getKakaoUserInfo(String kakaoAcessToken) {
            RestTemplate rt2 = new RestTemplate();

            HttpHeaders headers2 = new HttpHeaders();
            headers2.add("Authorization", "Bearer " + kakaoAcessToken);
            headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest2 = new HttpEntity<>(headers2);

            ResponseEntity<String> response2 = rt2.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    kakaoTokenRequest2,
                    String.class
            );
            //카카오 정보 받아옴
            ObjectMapper objectMapper2 = new ObjectMapper();
            KakaoUserDto kakaoUserDto = null;
            try {
                kakaoUserDto = objectMapper2.readValue(response2.getBody(), KakaoUserDto.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            authService.signUp(kakaoUserDto.toSignupRequestDto());

            try {
                //카카오 계정이 없을 시에 회원가입
                if (!authService.isEmailDuplicate(kakaoUserDto.getKakao_account().getEmail())) {
                    authService.signUp(kakaoUserDto.toSignupRequestDto());
                }
            } catch (HomealoneException e) {}

            return kakaoUserDto;
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
