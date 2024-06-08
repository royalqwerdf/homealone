package com.elice.homealone.member.controller;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.KakaoUserDto;
import com.elice.homealone.member.dto.OAuthTokenDto;
import com.elice.homealone.member.dto.request.LoginRequestDto;
import com.elice.homealone.member.dto.request.SignupRequestDto;
import com.elice.homealone.member.dto.response.LoginResponseDto;
import com.elice.homealone.member.service.AuthService;
import com.elice.homealone.member.service.OAuthService;
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
    private final OAuthService oAuthService;
    
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
        OAuthTokenDto oAuthTokenDto = oAuthService.getAccessToken(code);
        KakaoUserDto kakaoUserDto = oAuthService.getKakaoUserInfo(oAuthTokenDto);
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
