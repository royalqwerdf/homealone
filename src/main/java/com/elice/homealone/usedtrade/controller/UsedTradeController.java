package com.elice.homealone.usedtrade.controller;

import com.elice.homealone.usedtrade.service.UsedTradeImageService;
import com.elice.homealone.usedtrade.service.UsedTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsedTradeController {

    private final UsedTradeService usedTradeService;
    private final UsedTradeImageService usedtradeImageService;


}
