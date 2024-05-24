package com.elice.homealone.member.controller;


import com.elice.homealone.member.dto.MemberDto;
import com.elice.homealone.member.dto.request.LoginRequestDTO;
import com.elice.homealone.member.dto.request.SignupRequestDTO;
import com.elice.homealone.member.dto.response.LoginResponseDTO;
import com.elice.homealone.member.service.AuthService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<MemberDto> signUp(@RequestBody SignupRequestDTO signupRequestDTO) {
        MemberDto memberDto = authService.signUp(signupRequestDTO);
        return new ResponseEntity<>(memberDto, HttpStatus.CREATED);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            //로그인 성공
            LoginResponseDTO response = authService.login(loginRequestDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            //로그인 실패
            LoginResponseDTO response = new LoginResponseDTO();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }




}
