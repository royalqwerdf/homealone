package com.elice.homealone.module.usedtrade.dto;

import com.elice.homealone.module.member.dto.MemberDto;
import com.elice.homealone.module.tag.dto.PostTagDto;
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
    private String mainImage;
    private List<UsedTradeImageDto> images = new ArrayList<>();
    private List<PostTagDto> tags = new ArrayList<>();
    private MemberDto member;
    private int likeCount;


}
