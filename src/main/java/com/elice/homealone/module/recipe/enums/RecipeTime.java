package com.elice.homealone.module.recipe.enums;

import lombok.Getter;

@Getter
public enum RecipeTime {
    FIFTEEN(15),
    THIRTY(30),
    FORTY_FIVE(45),
    SIXTY(60),
    NINETY(90),
    ONE_TWENTY(120);

    private final int time;

    RecipeTime(int time) {
        this.time = time;
    }
}
