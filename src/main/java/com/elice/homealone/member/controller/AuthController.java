package com.elice.homealone.member.controller;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.KakaoUserDto;
import com.elice.homealone.member.dto.OAuthTokenDto;
import com.elice.homealone.member.dto.request.LoginRequestDto;
import com.elice.homealone.member.dto.request.SignupRequestDto;
import com.elice.homealone.member.dto.response.LoginResponseDto;
import com.elice.homealone.member.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "AuthController", description = "인증 관리 API")
public class AuthController {
    private final AuthService authService;
    
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignupRequestDto signupRequestDTO) {
        authService.signUp(signupRequestDTO);
        return new ResponseEntity<>("회원가입에 성공했습니다.", HttpStatus.OK);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDTO,
                                                  HttpServletResponse response) {
        //로그인 성공
        LoginResponseDto loginResponseDTO = authService.login(loginRequestDTO, response);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", loginResponseDTO.getAccessToken());
        return new ResponseEntity<>(loginResponseDTO, headers, HttpStatus.OK);
    }
    @Operation(summary = "카카오 로그인")
    @GetMapping("/kakao/callback")
    public  ResponseEntity<LoginResponseDto> login(String code, HttpServletResponse httpServletResponse) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "03ad25e0e92f5d885ecab9be8a98c3db");
        params.add("redirect_uri", "34.64.55.198/api/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
                HttpMethod.POST, // 요청할 방식
                kakaoTokenRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthTokenDto oAuthTokenDTO = null;
        try {
            oAuthTokenDTO = objectMapper.readValue(response.getBody(), OAuthTokenDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthTokenDTO.getAccess_token());
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

        //자동 로그인
        LoginResponseDto loginResponseDTO = authService.login(kakaoUserDto.toLoginRequestDto(), httpServletResponse);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", loginResponseDTO.getAccessToken());

        return new ResponseEntity<>(loginResponseDTO, httpHeaders, HttpStatus.OK);
    }


    @Operation(summary = "로그아웃")
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse){
        authService.logout(httpServletRequest, httpServletResponse);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
