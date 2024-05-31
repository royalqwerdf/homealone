package com.elice.homealone.usedtrade.dto;

import com.elice.homealone.usedtrade.entity.UsedTradeImage;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsedTradeResponseDto {

    private Long id;
    private String title;
    private int price;
    private String location;
    private String content;
    private UsedTradeImage mainImage;
    private List<UsedTradeImage> images = new ArrayList<>();


}
