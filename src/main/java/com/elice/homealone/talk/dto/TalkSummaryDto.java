package com.elice.homealone.talk.dto;

import com.elice.homealone.post.entity.Post;
import com.elice.homealone.talk.entity.Talk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalkSummaryDto {
    private Long id;
    private Post.Type type;
    private String title;
    private String memberName;
    private Integer commentCount;
    private Date createdAt;

    public static TalkSummaryDto totalkSummaryDto(Talk talk){
        return TalkSummaryDto.builder()
                .id(talk.getId())
                .type(talk.getType())
                .title(talk.getTitle())
                .memberName(talk.getMember().getName())
                .commentCount(talk.getComments().size())
                .createdAt(talk.getCreatedAt())
                .build();
    }
}
