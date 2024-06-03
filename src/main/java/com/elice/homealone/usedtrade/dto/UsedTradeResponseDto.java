package com.elice.homealone.usedtrade.dto;

import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.tag.dto.PostTagDto;
import com.elice.homealone.tag.entity.PostTag;
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
    private String mainImage;
    private List<UsedTradeImageDto> images = new ArrayList<>();
    private List<PostTagDto> tags = new ArrayList<>();
    private MemberDTO member;


}
