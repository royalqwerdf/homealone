package com.elice.homealone.member.controller;


import com.elice.homealone.member.dto.MemberDto;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.AuthService;
import com.elice.homealone.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "MemberController", description = "회원 관리 API")
public class MemberController {
    private final AuthService authService;
    private final MemberService memberService;

    @Operation(summary = "마이페이지 정보 조회")
    @GetMapping("/mypage/me")
    public ResponseEntity<MemberDto> getMemberInfo() {
        return ResponseEntity.ok(authService.getMember().toDto());
    }

    @Operation(summary = "마이페이지 정보 수정")
    @PatchMapping("/mypage/me")
    public ResponseEntity<MemberDto> editMemberInfo(@RequestBody MemberDto memberDTO){
        MemberDto changedMember = authService.editMember(memberDTO).toDto();
        return ResponseEntity.ok(changedMember);
    }

    @Operation(summary = "이메일 중복 체크")
    @GetMapping("/mypage/me/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        authService.isEmailDuplicate(email);
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }

    @Operation(summary = "계정 탈퇴")
    @PatchMapping("/mypage/me/withdrawal")
    public ResponseEntity<String> withdrawal() {
        authService.withdrawal(authService.getMember());
        return ResponseEntity.ok("회원 탈퇴가 완료됐습니다.");
    }

    @Operation(summary = "전체 회원 조회")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/adimin/member")
    public ResponseEntity<Page<Member>> getAllMember(@PageableDefault(size = 3) Pageable pageable) {
        Page<Member> members = memberService.findAll(pageable);
        return ResponseEntity.ok(members);
    }

    @Operation(summary = "회원 조회")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/adimin/member/{memberId}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable Long memberId) {
        MemberDto memberDTO = memberService.findById(memberId).toDto();
        return ResponseEntity.ok(memberDTO);
    }

    @Operation(summary = "회원 삭제")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/adimin/member/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        authService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
