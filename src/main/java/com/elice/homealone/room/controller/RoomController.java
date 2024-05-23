package com.elice.homealone.room.controller;

import com.elice.homealone.room.dto.RoomDto;
import com.elice.homealone.room.dto.RoomSummaryDto;
import com.elice.homealone.room.service.RoomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("")
    public ResponseEntity<Page<RoomSummaryDto>> findAll(@PageableDefault(size = 20) Pageable pageable){
        return ResponseEntity.ok(roomService.findAll(pageable));
    }

    @PostMapping("")
    public ResponseEntity<?> createRoomPost(@RequestBody @Validated RoomDto roomDto,
                                                              BindingResult bindingResult // 여기에 사용자 권한 추가 후 사용자 검증 로직
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
    public ResponseEntity<String> deletePost(@PathVariable Long roomId){
        roomService.deleteRoomPost(roomId);
        return ResponseEntity.ok().body("Room id: "+roomId+" post deleted successfully");
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto.RoomInfoDto> findRoomById (@PathVariable Long roomId){
        RoomDto.RoomInfoDto byRoomId = roomService.findByRoomId(roomId);
        return ResponseEntity.ok().body(byRoomId);

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchRoom(@RequestParam String query, Pageable pageable){
        if(query.length()==0){
            ///검색어를 입력하라ㅡㄴ 에러
        }
        Page<RoomSummaryDto> roomSummaryDtos = roomService.searchRoomPost(query, pageable);
        if(roomSummaryDtos.getSize() ==0){
            return ResponseEntity.ok().body("검색 결과가 없습니다.");
        }
        return ResponseEntity.ok().body(roomSummaryDtos);

    }

//    @GetMapping("/member") 회원으로 조회
}
