package com.elice.homealone.module.scrap.dto;

import com.elice.homealone.module.scrap.entity.Scrap;
import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.post.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapReqDto {

    private Long postId;

    public Scrap toEntity(Member member, Post post) {
        return Scrap.builder()
                .member(member)
                .post(post)
                .build();
    }
}
