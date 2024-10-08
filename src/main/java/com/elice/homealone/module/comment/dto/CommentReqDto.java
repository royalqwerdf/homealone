package com.elice.homealone.module.comment.dto;

import com.elice.homealone.module.comment.entity.Comment;
import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.post.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReqDto {

    private Long id;
    private String content;
    private Long postId;

    public Comment toEntity(Member member, Post post) {
        Comment comment = Comment.builder()
            .content(this.content)
            .member(member)
            .post(post)
            .build();
        post.addComment(comment);
        return comment;
    }
}
