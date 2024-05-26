package com.elice.homealone.room.controller;

import com.elice.homealone.room.dto.RoomDto;
import com.elice.homealone.room.dto.RoomSummaryDto;
import com.elice.homealone.room.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam(required = false) String title,
                                                        @RequestParam(required = false) String content,
                                                        @RequestParam(required = false) Long memberId,
                                                        @PageableDefault(size = 20) Pageable pageable){
        Page<RoomSummaryDto> roomSummaryDtos = roomService.searchRoomPost(title, content, memberId, pageable);
        if(!(title == null || title.isBlank()) || !(content == null && content.isBlank())){
            if(roomSummaryDtos.isEmpty()){
                return ResponseEntity.ok("검색 결과가 없습니다.");
            }
        }
        if(memberId != null){
            if(roomSummaryDtos.isEmpty()){
                return ResponseEntity.ok("작성하신 게시글이 없습니다.");
            }
        }
        return ResponseEntity.ok().body(roomSummaryDtos);
    }

    @PostMapping("")
    public ResponseEntity<?> createRoomPost(@RequestBody @Validated RoomDto roomDto,
                                                              BindingResult bindingResult // 여기에 회원 검증로직 추가해야함
                                                                ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            // 클라이언트에게 유효성 검사 실패 메시지 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        //사용자 검증 로직

        RoomDto.RoomInfoDto roomInfoDto = roomService.CreateRoomPost(roomDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomInfoDto);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<?> editRoomPost(@PathVariable Long roomId
                                            ,@RequestBody @Validated RoomDto roomDto,
                                          BindingResult bindingResult //사용자 받아 글쓴 회원과 일치하는지 확인 로직 추가
                                            ){
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            // 클라이언트에게 유효성 검사 실패 메시지 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        //사용자 검증 로직

        RoomDto.RoomInfoDto roomInfoDto = roomService.EditRoomPost(roomId,roomDto);
        return ResponseEntity.status(HttpStatus.OK).body(roomInfoDto);

    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deletePost(@PathVariable Long roomId){//사용자 받아 글쓴 회원과 일치하는지 확인 로직 추가
        roomService.deleteRoomPost(roomId);
        return ResponseEntity.ok().body("Room id: "+roomId+" post deleted successfully");
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto.RoomInfoDto> findRoomById (@PathVariable Long roomId){
        RoomDto.RoomInfoDto byRoomId = roomService.findByRoomId(roomId);
        return ResponseEntity.ok().body(byRoomId);

    }




}
