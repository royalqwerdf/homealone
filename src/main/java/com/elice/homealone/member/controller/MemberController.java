package com.elice.homealone.member.controller;


import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        MemberDTO member = authService.findLoginMemberByToken(token);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    /**
     * 로그인한 회원 내 정보 수정
     */
    @PatchMapping("/me")
    public ResponseEntity<MemberDTO> editMemberInfo(@RequestHeader(value="Authorization", required = true) String token,
                                                    @RequestBody MemberDTO memberDTO){
        MemberDTO changedMember = authService.editMember(memberDTO, token).toDto();
        changedMember.setMessage("회원 정보가 수정되었습니다.");
        return new ResponseEntity<>(changedMember,HttpStatus.OK);
    }

    /**
     * 로그인한 회원 탈퇴
     */
    @PatchMapping("/me/withdrawal")
    public ResponseEntity<MemberDTO> withdrawal(@RequestHeader(value = "Authorization", required = true) String token) {
        MemberDTO member = authService.findLoginMemberByToken(token);
        MemberDTO withdrawaledMember = authService.withdrawal(member);
        withdrawaledMember.setMessage("회원 탈퇴가 완료되었습니다.");
        return new ResponseEntity<>(withdrawaledMember, HttpStatus.OK);
    }

}
