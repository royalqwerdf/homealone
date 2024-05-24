package com.elice.homealone.usedtrade.controller;

import com.elice.homealone.usedtrade.dto.UsedTradeResponseDto;
import com.elice.homealone.usedtrade.entity.UsedTrade;
import com.elice.homealone.usedtrade.service.UsedTradeImageService;
import com.elice.homealone.usedtrade.service.UsedTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usedtrade")
public class UsedTradeController {

    private final UsedTradeService usedTradeService;
    private final UsedTradeImageService usedtradeImageService;

    //중고거래 전체 조회
    @GetMapping
    public ResponseEntity<Page<UsedTradeResponseDto>> getAllUsedTrades(@RequestParam int page, @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UsedTradeResponseDto> responseDtos = usedTradeService.getAllUsedTrades(pageable).map(UsedTrade::toDto);

        return ResponseEntity.ok(responseDtos);
    }




}
