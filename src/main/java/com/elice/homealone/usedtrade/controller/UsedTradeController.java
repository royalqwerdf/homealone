package com.elice.homealone.usedtrade.controller;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.usedtrade.dto.UsedTradeRequestDto;
import com.elice.homealone.usedtrade.dto.UsedTradeResponseDto;
import com.elice.homealone.usedtrade.service.UsedTradeImageService;
import com.elice.homealone.usedtrade.service.UsedTradeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usedtrade")
public class UsedTradeController {

    private final UsedTradeService usedTradeService;

    //중고거래 전체 조회
    @GetMapping
    public ResponseEntity<Page<UsedTradeResponseDto>> getAllUsedTrades(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UsedTradeResponseDto> responseDtos = usedTradeService.getAllUsedTrades(pageable);

        return new ResponseEntity<>(responseDtos,HttpStatus.OK);
    }
    //중고거래 상세페이지 조회
    @GetMapping("/{usedtradeId}")
    public ResponseEntity<UsedTradeResponseDto> getUsedTrade(@PathVariable("usedtradeId") Long usedtradeId) {

        UsedTradeResponseDto responseDto = usedTradeService.getUsedTrade(usedtradeId);

        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    //중고거래 게시글 작성
    @PostMapping
    public ResponseEntity<Long> createUsedTrade(@RequestBody UsedTradeRequestDto usedTradeRequestDto) {

        Long usedTradeId = usedTradeService.createUsedTrade(usedTradeRequestDto);

        return new ResponseEntity<>(usedTradeId,HttpStatus.CREATED);
    }

    //중고거래 게시글 수정
    @PutMapping("/{usedtradeId}")
    public ResponseEntity<String> updateUsedTrade(@RequestBody UsedTradeRequestDto requestDto, @PathVariable("usedtradeId") Long usedtradeId) {

        usedTradeService.modifyUsedTrade(usedtradeId,requestDto);

        return new ResponseEntity<>("수정 성공",HttpStatus.OK);
    }

    //중고거래 게시글을 조회하고
    //있다면 삭제하고 OK 반환
    @DeleteMapping("/{usedtradeId}")
    public ResponseEntity<String> deleteUsedTrade(@PathVariable("usedtradeId") Long usedtradeId) {

        usedTradeService.deleteUsedTrade(usedtradeId);

        return new ResponseEntity<>("삭제 완료",HttpStatus.OK);

    }

    //중고거래 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<Map<String,Object>> searchUsedTrades(
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
