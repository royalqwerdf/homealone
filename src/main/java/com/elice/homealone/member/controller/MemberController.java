package com.elice.homealone.member.controller;


import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MemberController {
    private final AuthService authService;

    /**
     * 로그인한 회원 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<MemberDTO> getMemberInfo(@AuthenticationPrincipal Member member) {
        return new ResponseEntity<>(member.toDto(), HttpStatus.OK);
    }

    /**
     * 로그인한 회원 내 정보 수정
     */
    @PatchMapping("/me")
    public ResponseEntity<MemberDTO> editMemberInfo(@AuthenticationPrincipal Member member,
                                                    @RequestBody MemberDTO memberDTO){
        MemberDTO changedMember = authService.editMember(member, memberDTO).toDto();
        return new ResponseEntity<>(changedMember,HttpStatus.OK);
    }

    /**
     * 로그인한 회원 탈퇴
     */
    @PatchMapping("/me/withdrawal")
    public ResponseEntity<MemberDTO> withdrawal(@AuthenticationPrincipal Member member) {
        MemberDTO withdrawaledMember = authService.withdrawal(member);
        return new ResponseEntity<>(withdrawaledMember, HttpStatus.OK);
    }

}
