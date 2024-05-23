package com.elice.homealone.usedtrade.service;

import com.elice.homealone.usedtrade.repository.UsedTradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsedTradeService {

    private final UsedTradeRepository usedTradeRepository;


}
