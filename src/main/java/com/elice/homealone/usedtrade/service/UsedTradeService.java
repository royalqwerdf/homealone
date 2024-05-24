package com.elice.homealone.usedtrade.service;

import com.elice.homealone.usedtrade.dto.UsedTradeResponseDto;
import com.elice.homealone.usedtrade.entity.UsedTrade;
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

    public Page<UsedTrade> getAllUsedTrades(Pageable pageable) {
        Page<UsedTrade> usedTrades = usedTradeRepository.findAll(pageable);
        return usedTrades;
    }


}
