package com.elice.homealone.scrap.dto;

import com.elice.homealone.scrap.entity.Scrap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapResDto {
    private Long id;
    private Long postId;
    private Long memberId;
    private String memberName;

    @Builder
    public ScrapResDto(Long id, Long postId, Long memberId, String memberName) {
        this.id = id;
        this.postId = postId;
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public static ScrapResDto fromEntity(Scrap scrap) {
        return ScrapResDto.builder()
                .id(scrap.getId())
                .postId(scrap.getPost().getId())
                .memberId(scrap.getMember().getId())
                .memberName(scrap.getMember().getName())
                .build();
    }
}
