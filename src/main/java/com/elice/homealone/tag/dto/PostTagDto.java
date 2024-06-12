package com.elice.homealone.tag.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class PostTagDto {

    private Long id;
    private String tagName;

    @Builder
    public PostTagDto(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}
