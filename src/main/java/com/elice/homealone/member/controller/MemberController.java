package com.elice.homealone.member.controller;


import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MemberController {
    private final AuthService authService;

    /**
     * Token으로 로그인한 회원 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<MemberDTO> getMemberInfo(@RequestHeader(value="Authorization", required = true) String token) {
        String accessToken = token.substring(7);
        MemberDTO member = new MemberDTO();
        try {
            //토큰으로 memberDTO 반환
            member = authService.findLoginMemberByToken(accessToken).toDto();
            member.setMessage("회원정보가 성공적으로 조회되었습니다.");
            return new ResponseEntity<>(member, HttpStatus.OK);
        } catch (HomealoneException e) {
            member.setMessage(e.getErrorCode().getMessage());
            return new ResponseEntity<>(member, e.getErrorCode().getHttpStatus());
        }
    }




}
