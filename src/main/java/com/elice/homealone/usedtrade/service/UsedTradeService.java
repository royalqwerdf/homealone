package com.elice.homealone.usedtrade.service;

import com.elice.homealone.usedtrade.dto.UsedTradeRequestDto;
import com.elice.homealone.usedtrade.dto.UsedTradeResponseDto;
import com.elice.homealone.usedtrade.entity.UsedTrade;
import com.elice.homealone.usedtrade.repository.UsedTradeImageRepository;
import com.elice.homealone.usedtrade.repository.UsedTradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsedTradeService {

    private final UsedTradeRepository usedTradeRepository;
    private final UsedTradeImageRepository usedTradeImageRepository;

    //모든 중고거래 조회
    public Page<UsedTradeResponseDto> getAllUsedTrades(Pageable pageable) {
        Page<UsedTrade> usedTrades = usedTradeRepository.findAll(pageable);
        Page<UsedTradeResponseDto> usedTradesDto = usedTrades.map(UsedTrade::toAllListDto);
        return usedTradesDto;
    }

    //중고거래 게시글 수정
    public boolean modifyUsedTrade(Long id, UsedTradeRequestDto requestDto) {
        UsedTrade usedTrade = usedTradeRepository.findById(id).orElse(null);
        if(usedTrade == null) {
            return false;
        }
        if(!(requestDto.getTitle()==null)){
            usedTrade.setTitle(requestDto.getTitle());
        }
        if(!(requestDto.getPrice()==0)){
            usedTrade.setPrice(requestDto.getPrice());
        }
        if(!(requestDto.getLocation()==null)){
            usedTrade.setLocation(requestDto.getLocation());
        }
        if(!(requestDto.getContent()==null)){
            usedTrade.setContent(requestDto.getContent());
        }
        usedTradeRepository.save(usedTrade);
        return true;
    }

    //중고거래 게시글 삭제
    public boolean deleteUsedTrade(Long id) {
        UsedTrade usedTrade = usedTradeRepository.findById(id).orElse(null);
        if(usedTrade == null) {
            return false;
        }
        usedTradeRepository.delete(usedTrade);
        return true;
    }


}
