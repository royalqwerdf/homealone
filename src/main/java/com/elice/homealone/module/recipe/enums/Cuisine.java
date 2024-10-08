package com.elice.homealone.module.recipe.enums;

import lombok.Builder;
import lombok.Getter;

@Getter
public enum Cuisine {
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    ITALIAN("이탈리아 음식"),
    FRENCH("프랑스 음식"),
    INDIAN("인도 음식"),
    MEXICAN("멕시코 음식"),
    THAI("태국 음식"),
    ETC("기타");

    private final String cuisine;

    Cuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
