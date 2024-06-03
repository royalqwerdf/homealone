package com.elice.homealone.member.controller;


import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.dto.request.LoginRequestDTO;
import com.elice.homealone.member.dto.request.SignupRequestDTO;
import com.elice.homealone.member.dto.response.LoginResponseDTO;
import com.elice.homealone.member.dto.response.SignupResponseDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.AuthService;
import com.elice.homealone.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signUp(@RequestBody SignupRequestDTO signupRequestDTO) {
        SignupResponseDTO response = new SignupResponseDTO();
        try{
            response = authService.signUp(signupRequestDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(HomealoneException e){
            response.setMessage(e.getErrorCode().getMessage());
            return new ResponseEntity<>(response, e.getErrorCode().getHttpStatus());
        }
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            //로그인 성공
            LoginResponseDTO response = authService.login(loginRequestDTO);

            HttpHeaders headers = new   HttpHeaders();
            headers.set("Authorization", response.getAccessToken());

            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch (HomealoneException e) {
            //로그인 실패
            LoginResponseDTO response = new LoginResponseDTO();
            response.setMessage(e.getErrorCode().getMessage());
            return new ResponseEntity<>(response, e.getErrorCode().getHttpStatus());
        }
    }

    /**
     * 로그아웃
     */


    /**
     * 회원 정보 전체 조회
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<Page<Member>> getAllMember(@RequestHeader(value="Authorization", required = true) String token,
                                                        @PageableDefault(size = 20) Pageable pageable){
        Page<Member> members = memberService.findAll(pageable);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    /**
     * 회원 삭제
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<Long> deleteMember(@RequestHeader(value="Authorization", required = true) String token,
                                             @PathVariable Long memberId) {
        authService.deleteMember(memberId, token);
        return new ResponseEntity<>(memberId, HttpStatus.OK);
    }



}
