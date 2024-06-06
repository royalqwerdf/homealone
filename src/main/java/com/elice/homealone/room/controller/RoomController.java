package com.elice.homealone.room.controller;

import com.elice.homealone.global.exception.Response;
import com.elice.homealone.room.dto.RoomRequestDTO;
import com.elice.homealone.room.dto.RoomResponseDTO;
import com.elice.homealone.room.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam(required = false) String title,
                                                        @RequestParam(required = false) String content,
                                                        @RequestParam(required = false) String tag,
                                                        @RequestParam(required = false) Long memberId,
                                                        @PageableDefault(size = 20) Pageable pageable){
        Page<RoomResponseDTO> RoomResponseDTO = roomService.searchRoomPost(title, content,tag, memberId, pageable);
            if(RoomResponseDTO.isEmpty()){
                Response.ApiResponse response = new Response.ApiResponse("결과가 없습니다.");
                return ResponseEntity.ok(response);
            }
        return ResponseEntity.ok().body(RoomResponseDTO);
    }

    @PostMapping("")
    public ResponseEntity<RoomResponseDTO.RoomInfoDto> createRoomPost(@Validated @RequestBody RoomRequestDTO roomDto,
                                           @AuthenticationPrincipal CustomUserDetails userDetails
                                                                ) {

        //사용자 검증 로직
        String username = userDetails.getUsername();
        RoomResponseDTO.RoomInfoDto roomInfoDto = roomService.CreateRoomPost(roomDto,username);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomInfoDto);
    }

    @PatchMapping("/{roomId}")
    public ResponseEntity<RoomResponseDTO.RoomInfoDto> editRoomPost(@PathVariable Long roomId
                                            , @Validated @RequestBody RoomRequestDTO roomDto//사용자 받아 글쓴 회원과 일치하는지 확인 로직 추가
                                            ,  @AuthenticationPrincipal CustomUserDetails userDetails){
        //사용자 검증 로직
        String username = userDetails.getUsername();
        RoomResponseDTO.RoomInfoDto roomInfoDto = roomService.EditRoomPost(username,roomId,roomDto);
        return ResponseEntity.status(HttpStatus.OK).body(roomInfoDto);

    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Response.ApiResponse> deletePost(@PathVariable Long roomId
            ,  @AuthenticationPrincipal CustomUserDetails userDetails){//사용자 받아 글쓴 회원과 일치하는지 확인 로직 추가

        String username = userDetails.getUsername();
        roomService.deleteRoomPost(username,roomId);
        Response.ApiResponse response = new Response.ApiResponse("방자랑 "+roomId+"번 게시글이 성공적으로 지워졌습니다.");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> findRoomById(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long roomId) {
        RoomResponseDTO roomInfo;

        if (userDetails != null) {
            String username = userDetails.getUsername();
            roomInfo = roomService.findByRoomIdForMember(roomId, username);
        } else {
            roomInfo = roomService.findByRoomId(roomId);
        }

        return ResponseEntity.ok(roomInfo);
    }



}
