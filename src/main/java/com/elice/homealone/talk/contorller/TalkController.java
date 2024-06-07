package com.elice.homealone.talk.contorller;

import com.elice.homealone.global.exception.Response;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.talk.Service.TalkService;
import com.elice.homealone.talk.dto.TalkRequestDTO;
import com.elice.homealone.talk.dto.TalkResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/talk")
public class TalkController {
    @Autowired
    private TalkService talkService;
//TODO: 검색 검증로직 컨트롤러 단에만 추가하도록
    @GetMapping("")
    public ResponseEntity<Page<TalkResponseDTO>> findAll(@RequestParam(required = false) String title,
                                                        @RequestParam(required = false) String content,
                                                        @RequestParam(required = false) String tag,
                                                        @RequestParam(required = false) Long memberId,
                                                        @PageableDefault(size = 20) Pageable pageable){

        Page<TalkResponseDTO> talkSummaryDtos = talkService.searchTalkPost(title, content,tag, memberId, pageable);
        return ResponseEntity.ok().body(talkSummaryDtos);
    }

    @PostMapping("")
    public ResponseEntity<?> createRoomPost(@Validated @RequestBody TalkRequestDTO talkDto,
                                            @AuthenticationPrincipal Member member
                                                                ) {
        String email = member.getUsername();
        TalkResponseDTO.TalkInfoDto talkInfoDto = talkService.CreateTalkPost(talkDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(talkInfoDto);
    }

    @PatchMapping("/{talkId}")
    public ResponseEntity<?> editRoomPost(@PathVariable Long talkId
                                            , @Validated @RequestBody TalkRequestDTO talkDto//사용자 받아 글쓴 회원과 일치하는지 확인 로직 추가
                                            ,  @AuthenticationPrincipal Member member){
        String email = member.getUsername();
        TalkResponseDTO.TalkInfoDto talkInfoDto = talkService.EditTalkPost(email, talkId, talkDto);
        return ResponseEntity.status(HttpStatus.OK).body(talkInfoDto);

    }

    @DeleteMapping("/{talkId}")
    public ResponseEntity<?> deletePost(@PathVariable Long talkId
            , @AuthenticationPrincipal Member member){//사용자 받아 글쓴 회원과 일치하는지 확인 로직 추가
        String email = member.getUsername();
        talkService.deleteRoomPost(email,talkId);
        Response.ApiResponse response = new Response.ApiResponse("방자랑 "+talkId+"번 게시글이 성공적으로 지워졌습니다.");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{talkId}")
    public ResponseEntity<?> findRoomById (@AuthenticationPrincipal Member member,
            @PathVariable Long talkId){
        Object byRoomId;
        String email = member.getUsername();
        if(email == null || email.isEmpty())
        {
             byRoomId =  talkService.findByTalkId(talkId);
        }
        else {
             byRoomId = talkService.findByTalkIdForMember(talkId, email);
        }
        return ResponseEntity.ok().body(byRoomId);

    }

    @GetMapping("/topView")
    public ResponseEntity<Page<TalkResponseDTO>> findTopTalkByView(@PageableDefault(size = 5) Pageable pageable){
        Page<TalkResponseDTO> topTalkByView = talkService.findTopTalkByView(pageable);
        return ResponseEntity.ok(topTalkByView);
    }


}
