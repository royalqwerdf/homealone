package com.elice.homealone.tag.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TagDto {

    private String name;

    @Builder
    public TagDto(String name) {
        this.name = name;
    }
}
