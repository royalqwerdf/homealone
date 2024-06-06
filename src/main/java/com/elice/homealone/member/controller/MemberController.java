package com.elice.homealone.member.controller;


import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.AuthService;
import com.elice.homealone.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final AuthService authService;
    private final MemberService memberService;

    @Operation(summary = "마이페이지 정보 조회")
    @GetMapping("/mypage/me")
    public ResponseEntity<MemberDTO> getMemberInfo(@AuthenticationPrincipal Member member) {
        MemberDTO memberDTO = memberService.findById(member.getId()).toDto();
        return ResponseEntity.ok(memberDTO);
    }

    @Operation(summary = "마이페이지 정보 수정")
    @PatchMapping("/mypage/me")
    public ResponseEntity<MemberDTO> editMemberInfo(@AuthenticationPrincipal Member member,
                                                    @RequestBody MemberDTO memberDTO){
        MemberDTO changedMember = authService.editMember(member, memberDTO).toDto();
        return ResponseEntity.ok(changedMember);
    }

    @Operation(summary = "이메일 중복 체크")
    @GetMapping("/mypage/me/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        authService.emailExists(email);
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }

    @Operation(summary = "계정 탈퇴")
    @PatchMapping("/mypage/me/withdrawal")
    public ResponseEntity<MemberDTO> withdrawal(@AuthenticationPrincipal Member member) {
        MemberDTO withdrawaledMember = authService.withdrawal(member);
        return ResponseEntity.ok(withdrawaledMember);
    }

    @Operation(summary = "전체 회원 조회")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/member")
    public ResponseEntity<Page<Member>> getAllMember(@PageableDefault(size = 3) Pageable pageable) {
        Page<Member> members = memberService.findAll(pageable);
        return ResponseEntity.ok(members);
    }

    @Operation(summary = "회원 조회")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long memberId) {
        MemberDTO memberDTO = memberService.findById(memberId).toDto();
        return ResponseEntity.ok(memberDTO);
    }

    @Operation(summary = "회원 삭제")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        authService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
