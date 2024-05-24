package com.elice.homealone.talk.dto;

import com.elice.homealone.talk.entity.Talk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalkSummaryDto {
    private Long id;
    private String title;
    private String memberName;
    private Integer commentCount;
    private LocalDateTime createdAt;

    public static TalkSummaryDto totalkSummaryDto(Talk talk){
        return TalkSummaryDto.builder()
                .id(talk.getId())
                .title(talk.getTitle())
                .memberName(talk.getMember().getName())
                .commentCount(talk.getComments().size())
                .createdAt(talk.getCreatedAt())
                .build();
    }
}
