package com.elice.homealone.tag.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostTagDto {

    private Long id;

    private String name;

    @Builder
    public PostTagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
