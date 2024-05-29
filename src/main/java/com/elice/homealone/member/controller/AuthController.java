package com.elice.homealone.member.controller;


import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.request.LoginRequestDTO;
import com.elice.homealone.member.dto.request.SignupRequestDTO;
import com.elice.homealone.member.dto.response.LoginResponseDTO;
import com.elice.homealone.member.dto.response.SignupResponseDTO;
import com.elice.homealone.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signUp(@RequestBody SignupRequestDTO signupRequestDTO) {
        SignupResponseDTO response = authService.signUp(signupRequestDTO);
        //일단 회원가입이 성공하든 실패하든 HttpStatus.CREATE로 보내게 만듬
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            //로그인 성공
            LoginResponseDTO response = authService.login(loginRequestDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", response.getAccessToken());

            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch (HomealoneException e) {
            //로그인 실패
            LoginResponseDTO response = new LoginResponseDTO();
            response.setMessage(e.getErrorCode().getMessage());
            return new ResponseEntity<>(response, e.getErrorCode().getHttpStatus());
        }
    }
}
