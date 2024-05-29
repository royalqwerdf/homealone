package com.elice.homealone.tag.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TagDto {

    private Long id;
    private String name;

    @Builder
    public TagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
