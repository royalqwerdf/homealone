package com.elice.homealone.tag.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostTagDto {

    private Long id;

    private String name;

    @Builder
    public PostTagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
