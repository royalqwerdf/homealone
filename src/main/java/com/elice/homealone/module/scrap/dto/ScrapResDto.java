package com.elice.homealone.module.scrap.dto;

import com.elice.homealone.module.scrap.entity.Scrap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapResDto {
    private Long id;
    private Long postId;
    private Long memberId;
    private String memberName;

    @Setter
    private int totalCount;

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
