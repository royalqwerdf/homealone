package com.elice.homealone.tag.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTagDto {

    private Long id;
    private String tagName;

    @Builder
    public PostTagDto(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}
