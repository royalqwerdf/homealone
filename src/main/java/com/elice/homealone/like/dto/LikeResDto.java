package com.elice.homealone.like.dto;

import com.elice.homealone.like.entity.Like;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeResDto {
    private Long id;
    private Long postId;
    private Long memberId;
    private String memberName;
    private LocalDateTime createdAt;

    @Builder
    public LikeResDto(
            Long id,
            Long postId,
            Long memberId,
            String memberName,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.postId = postId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.createdAt = createdAt;
    }

    public static LikeResDto fromEntity(Like like) {
        return LikeResDto.builder()
                .id(like.getId())
                .postId(like.getPost().getId())
                .memberId(like.getMember().getId())
                .memberName(like.getMember().getName())
                .createdAt(like.getCreatedAt())
                .build();
    }
}
