package com.elice.homealone.usedtrade.controller;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.usedtrade.dto.UsedTradeRequestDto;
import com.elice.homealone.usedtrade.dto.UsedTradeResponseDto;
import com.elice.homealone.usedtrade.service.UsedTradeImageService;
import com.elice.homealone.usedtrade.service.UsedTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usedtrade")
public class UsedTradeController {

    private final UsedTradeService usedTradeService;
    private final UsedTradeImageService usedtradeImageService;

    //중고거래 전체 조회
    @GetMapping
    public ResponseEntity<Map<String,?>> getAllUsedTrades(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UsedTradeResponseDto> responseDtos = usedTradeService.getAllUsedTrades(pageable);

        //Map으로 메시지와 중고거래 리스트를 반환
        Map<String,Object> response = new HashMap<>();
        response.put("data", responseDtos);

        //예시 메시지
        response.put("message", "전체 조회 성공");

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{usedtradeId}")
    public ResponseEntity<?> getUsedTrade(@PathVariable("usedtradeId") Long usedtradeId) {
        UsedTradeResponseDto responseDto = usedTradeService.getUsedTrade(usedtradeId);
        if(responseDto == null) {
            return new ResponseEntity<>(ErrorCode.POST_NOT_FOUND.getMessage(),ErrorCode.POST_NOT_FOUND.getHttpStatus());
        }
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUsedTrade(@RequestBody UsedTradeRequestDto usedTradeRequestDto) {
        if(!usedTradeService.createUsedTrade(usedTradeRequestDto)){
            return new ResponseEntity<>(usedTradeRequestDto,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("생성 완료",HttpStatus.CREATED);
    }

    @PutMapping("/{usedtradeId}")
    public ResponseEntity<String> updateUsedTrade(@RequestBody UsedTradeRequestDto requestDto, @PathVariable("usedtradeId") Long usedtradeId) {

        if(!usedTradeService.modifyUsedTrade(usedtradeId,requestDto)){
            return new ResponseEntity<>(ErrorCode.POST_NOT_FOUND.getMessage(),ErrorCode.POST_NOT_FOUND.getHttpStatus());
        }

        return new ResponseEntity<>("수정 성공",HttpStatus.OK);
    }

    //중고거래 게시글을 조회하고
    //있다면 삭제하고 OK 반환
    @DeleteMapping("/{usedtradeId}")
    public ResponseEntity<String> deleteUsedTrade(@PathVariable("usedtradeId") Long usedtradeId) {

        boolean isDeleted = usedTradeService.deleteUsedTrade(usedtradeId);

        if(!isDeleted){
            return new ResponseEntity<>(ErrorCode.POST_NOT_FOUND.getMessage(),ErrorCode.POST_NOT_FOUND.getHttpStatus());
        }

        return new ResponseEntity<>("삭제 완료",HttpStatus.OK);

    }

    //중고거래 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<Map<String,?>> searchUsedTrades(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "location", required = false) String location
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<UsedTradeResponseDto> responseDtos = usedTradeService.searchUsedTrades(pageable,title,content,location);

        Map<String,Object> response = new HashMap<>();
        String message = "검색 성공";
        if(responseDtos == null){
            message = "검색결과가 없습니다";
        }

        response.put("data",responseDtos);
        response.put("message",message);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }




}
