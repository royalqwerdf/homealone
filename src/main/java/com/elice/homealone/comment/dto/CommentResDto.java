package com.elice.homealone.comment.dto;

import com.elice.homealone.comment.entity.Comment;
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

    @Builder
    public CommentResDto(
        Long id,
        Long postId,
        Long memberId,
        String memberName,
        String content,
        LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.postId = postId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.content = content;
        this.modifiedAt = modifiedAt;
    }

    public static CommentResDto fromEntity(Comment comment) {
        return CommentResDto.builder()
            .id(comment.getId())
            .postId(comment.getPost().getId())
            .memberId(comment.getMember().getId())
            .memberName(comment.getMember().getName())
            .content(comment.getContent())
            .modifiedAt(comment.getModifiedAt())
            .build();
    }
}
