package com.elice.homealone.usedtrade.controller;

import com.elice.homealone.usedtrade.dto.UsedTradeRequestDto;
import com.elice.homealone.usedtrade.dto.UsedTradeResponseDto;
import com.elice.homealone.usedtrade.entity.UsedTrade;
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
@RequestMapping("/api/usedtrade")
public class UsedTradeController {

    private final UsedTradeService usedTradeService;
    private final UsedTradeImageService usedtradeImageService;

    //중고거래 전체 조회
    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllUsedTrades(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UsedTradeResponseDto> responseDtos = usedTradeService.getAllUsedTrades(pageable);

        //데이터가 없다면 204 NO_CONTENT 반환
        //todo 커스텀 상태코드 클래스를 만들어서 공동으로 사용하는게 좋아보임
        //todo API 구현을 우선으로 하고 추후 논의
        if(responseDtos.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        //Map으로 메시지와 중고거래 리스트를 반환
        Map<String,Object> response = new HashMap<>();
        response.put("data", responseDtos);

        //예시 메시지
        response.put("message", "전체 조회 성공");

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<UsedTradeResponseDto> createUsedTrade(@RequestBody UsedTradeRequestDto usedTradeRequestDto) {
//
//        return null;
//    }

    @PutMapping("/{usedtradeId}")
    public ResponseEntity<String> updateUsedTrade(@RequestBody UsedTradeRequestDto requestDto, @PathVariable("usedtradeId") Long usedtradeId) {

        if(!usedTradeService.modifyUsedTrade(usedtradeId,requestDto)){
            return new ResponseEntity<>("존재하지 않는 게시글입니다",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("수정 성공",HttpStatus.OK);
    }

    //중고거래 게시글을 조회하고
    //데이터가 없으면 BAD_REQUEST 반환
    //있다면 삭제하고 OK 반환
    @DeleteMapping("/{usedtradeId}")
    public ResponseEntity<String> deleteUsedTrade(@PathVariable("usedtradeId") Long usedtradeId) {

        boolean isDeleted = usedTradeService.deleteUsedTrade(usedtradeId);

        if(isDeleted){ return new ResponseEntity<>("삭제 실패",HttpStatus.BAD_REQUEST);}

        return new ResponseEntity<>("삭제 완료",HttpStatus.OK);

    }




}
