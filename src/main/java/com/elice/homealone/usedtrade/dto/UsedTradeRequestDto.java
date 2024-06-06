package com.elice.homealone.usedtrade.dto;

import com.elice.homealone.tag.entity.PostTag;
import com.elice.homealone.usedtrade.entity.UsedTrade;
import com.elice.homealone.usedtrade.entity.UsedTradeImage;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsedTradeRequestDto {

    private String title;
    private int price;
    private String location;
    private String content;
    private List<UsedTradeImage> images = new ArrayList<>();
    private Long memberId;
    private List<PostTag> tag;

    public UsedTrade toEntity(){
        return UsedTrade.builder()
                .title(this.title)
                .price(this.price)
                .location(this.location)
                .content(this.content)
                .images(this.images)
                .build();
    }

}
