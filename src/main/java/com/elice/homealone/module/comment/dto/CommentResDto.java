package com.elice.homealone.module.comment.dto;

import com.elice.homealone.module.comment.entity.Comment;
import com.elice.homealone.module.post.entity.Post;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResDto {
    private Long id;
    private Long postId;
    private Long memberId;
    private String memberName;
    private String content;
    private LocalDateTime modifiedAt;

    @Setter
    private boolean isLikeByCurrentUser = false;
    @Setter
    private int likeCount = 0;
    @Setter
    private String postTitle;
    @Setter
    private String postMemberName;
    @Setter
    private Post.Type postType;

    @Builder
    public CommentResDto(
        Long id,
        Long postId,
        Long memberId,
        String memberName,
        String content,
        LocalDateTime modifiedAt,
        int likeCount
    ) {
        this.id = id;
        this.postId = postId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.likeCount = likeCount;
    }

    public static CommentResDto fromEntity(Comment comment) {
        return CommentResDto.builder()
            .id(comment.getId())
            .postId(comment.getPost().getId())
            .memberId(comment.getMember().getId())
            .memberName(comment.getMember().getName())
            .content(comment.getContent())
            .modifiedAt(comment.getModifiedAt())
            .likeCount(comment.getLikes().size())
            .build();
    }
}
