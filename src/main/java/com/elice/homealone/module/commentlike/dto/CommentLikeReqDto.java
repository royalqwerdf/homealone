package com.elice.homealone.module.commentlike.dto;

import com.elice.homealone.module.commentlike.entity.CommentLike;
import com.elice.homealone.module.comment.entity.Comment;
import com.elice.homealone.module.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeReqDto {
    private Long id;
    private Long commentId;

    public CommentLike toEntity(Member member, Comment comment) {
        return CommentLike.builder()
            .member(member)
            .comment(comment)
            .build();
    }
}
