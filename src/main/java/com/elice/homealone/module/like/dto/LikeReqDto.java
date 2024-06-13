package com.elice.homealone.module.like.dto;

import com.elice.homealone.module.like.entity.Like;
import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.post.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReqDto {

    private Long id;
    private Long postId;
    public Like toEntity(Member member, Post post) {
        return Like.builder()
                .member(member)
                .post(post)
                .build();
    }
}
