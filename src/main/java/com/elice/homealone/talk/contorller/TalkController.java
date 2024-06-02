package com.elice.homealone.talk.contorller;

import com.elice.homealone.global.exception.Response;
import com.elice.homealone.talk.Service.TalkService;
import com.elice.homealone.talk.dto.TalkRequestDTO;
import com.elice.homealone.talk.dto.TalkResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/talk")
public class TalkController {
    @Autowired
    private TalkService talkService;
//TODO: 검색 검증로직 컨트롤러 단에만 추가하도록
    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam(required = false) String title,
                                                        @RequestParam(required = false) String content,
                                                        @RequestParam(required = false) String tag,
                                                        @RequestParam(required = false) Long memberId,
                                                        @PageableDefault(size = 20) Pageable pageable){

        Page<TalkResponseDTO> talkSummaryDtos = talkService.searchTalkPost(title, content,tag, memberId, pageable);
        if(!(title == null || title.isBlank()) || !(content == null && content.isBlank())){
            if(talkSummaryDtos.isEmpty()){
                Response.ApiResponse response = new Response.ApiResponse("검색 결과가 없습니다.");
                return ResponseEntity.ok(response);
            }
        }
        if(memberId != null){
            if(talkSummaryDtos.isEmpty()){
                Response.ApiResponse response = new Response.ApiResponse("작성하신 게시글이 없습니다.");
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.ok().body(talkSummaryDtos);
    }

    @PostMapping("")
    public ResponseEntity<?> createRoomPost(@Validated @RequestBody TalkRequestDTO talkDto,
                                            @RequestHeader("Authorization") String token
                                                                ) {
        TalkResponseDTO.TalkInfoDto talkInfoDto = talkService.CreateTalkPost(talkDto, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(talkInfoDto);
    }

    @PatchMapping("/{talkId}")
    public ResponseEntity<?> editRoomPost(@PathVariable Long talkId
                                            , @Validated @RequestBody TalkRequestDTO talkDto//사용자 받아 글쓴 회원과 일치하는지 확인 로직 추가
                                            , @RequestHeader("Authorization") String token){
        TalkResponseDTO.TalkInfoDto talkInfoDto = talkService.EditTalkPost(token, talkId, talkDto);
        return ResponseEntity.status(HttpStatus.OK).body(talkInfoDto);

    }

    @DeleteMapping("/{talkId}")
    public ResponseEntity<?> deletePost(@PathVariable Long talkId
            , @RequestHeader("Authorization") String token){//사용자 받아 글쓴 회원과 일치하는지 확인 로직 추가
        talkService.deleteRoomPost(token,talkId);
        Response.ApiResponse response = new Response.ApiResponse("방자랑 "+talkId+"번 게시글이 성공적으로 지워졌습니다.");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{talkId}")
    public ResponseEntity<?> findRoomById ( @RequestHeader(value = "Authorization",required = false) String token,
            @PathVariable Long talkId){
        Object byRoomId;
        if(token.isEmpty() || token == null)
        {
             byRoomId =  talkService.findByTalkId(talkId);
        }
        else {
             byRoomId = talkService.findByTalkIdForMember(talkId, token);
        }
        return ResponseEntity.ok().body(byRoomId);

    }




}
