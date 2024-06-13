package com.elice.homealone.module.usedtrade.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsedTradeImageDto {

    private Long id;
    private boolean main;
    private String url;

}
