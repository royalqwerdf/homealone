package com.elice.homealone.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostRelatedDto {

    private int likeCount;
    private int scrapCount;
    private int commentCount;

    @Setter
    private boolean isLikeByCurrentUser = false;
    @Setter
    private boolean isBookmarked = false;

    @Builder
    public PostRelatedDto(int likeCount, int scrapCount, int commentCount){
        this.likeCount = likeCount;
        this.scrapCount = scrapCount;
        this.commentCount = commentCount;
    }
}
