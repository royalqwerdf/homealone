package com.elice.homealone.module.commentlike.dto;

import com.elice.homealone.module.commentlike.entity.CommentLike;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeResDto {
    private Long id;
    private Long commentId;
    private Long memberId;
    private String memberName;
    private LocalDateTime createdAt;

    @Setter
    private int totalCount;

    @Builder
    public CommentLikeResDto(
        Long id,
        Long commentId,
        Long memberId,
        String memberName,
        LocalDateTime createdAt
    ) {
        this.id = id;
        this.commentId = commentId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.createdAt = createdAt;
    }

    public static CommentLikeResDto fromEntity(CommentLike commentLike) {
        return CommentLikeResDto.builder()
            .id(commentLike.getId())
            .commentId(commentLike.getComment().getId())
            .memberId(commentLike.getMember().getId())
            .memberName(commentLike.getMember().getName())
            .createdAt(commentLike.getCreatedAt())
            .build();
    }
}
