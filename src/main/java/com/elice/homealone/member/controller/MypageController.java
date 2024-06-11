package com.elice.homealone.member.controller;


import com.elice.homealone.member.dto.MemberDto;
import com.elice.homealone.member.service.AuthService;
import com.elice.homealone.room.dto.RoomResponseDTO;
import com.elice.homealone.room.service.RoomService;
import com.elice.homealone.talk.Service.TalkService;
import com.elice.homealone.talk.dto.TalkResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/me")
@Tag(name = "MypageController", description = "마이페이지 관리 API")
public class MypageController {
    private final AuthService authService;
    private final TalkService talkService;
    private final RoomService roomService;
    @Operation(summary = "마이페이지 정보 조회")
    @GetMapping("")
    public ResponseEntity<MemberDto> getMemberInfo() {
        return ResponseEntity.ok(authService.getMember().toDto());
    }

    @Operation(summary = "마이페이지 정보 수정")
    @PatchMapping("")
    public ResponseEntity<MemberDto> editMemberInfo(@RequestBody MemberDto memberDTO){
        MemberDto changedMember = authService.editMember(memberDTO).toDto();
        return ResponseEntity.ok(changedMember);
    }

    @Operation(summary = "방자랑 게시글 회원으로 조회")
    @GetMapping("/room")
    public ResponseEntity<Page<RoomResponseDTO>> findRoomByMember(@PageableDefault(size = 10) Pageable pageable){
        Page<RoomResponseDTO> roomByMember = roomService.findRoomByMember(pageable);
        return ResponseEntity.ok(roomByMember);
    }
    @Operation(summary = "혼잣말 게시글 회원으로 조회")
    @GetMapping("/talk")
    public ResponseEntity<Page<TalkResponseDTO>> findTalkByMember(@PageableDefault(size = 10) Pageable pageable){
        Page<TalkResponseDTO> talkByMember = talkService.findTalkByMember(pageable);
        return ResponseEntity.ok(talkByMember);
    }


    @Operation(summary = "이메일 중복 체크")
    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        authService.isEmailDuplicate(email);
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }

    @Operation(summary = "계정 탈퇴")
    @PatchMapping("/withdrawal")
    public ResponseEntity<String> withdrawal() {
        authService.withdrawal(authService.getMember());
        return ResponseEntity.ok("회원 탈퇴가 완료됐습니다.");
    }



}
