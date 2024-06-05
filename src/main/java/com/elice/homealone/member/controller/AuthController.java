package com.elice.homealone.member.controller;


import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.dto.request.LoginRequestDTO;
import com.elice.homealone.member.dto.request.SignupRequestDTO;
import com.elice.homealone.member.dto.response.LoginResponseDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.AuthService;
import com.elice.homealone.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignupRequestDTO signupRequestDTO) {
        authService.signUp(signupRequestDTO);
        return new ResponseEntity<>("회원가입에 성공했습니다.", HttpStatus.OK);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO,
                                                  HttpServletResponse response) {
        //로그인 성공
        LoginResponseDTO loginResponseDTO = authService.login(loginRequestDTO, response);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", loginResponseDTO.getAccessToken());
        return new ResponseEntity<>(loginResponseDTO, headers, HttpStatus.OK);
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse){
        authService.logout(httpServletRequest, httpServletResponse);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 회원 목록 조회
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/member")
    public ResponseEntity<Page<Member>> getAllMember(@PageableDefault(size = 3) Pageable pageable) {
        Page<Member> members = memberService.findAll(pageable);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    /**
     * 회원 조회
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long memberId) {
        MemberDTO memberDTO = memberService.findById(memberId).toDto();
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    /**
     * 회원 삭제
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        authService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
