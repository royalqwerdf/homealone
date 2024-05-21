package com.elice.homealone.recipe.enums;

import lombok.Getter;

@Getter
public enum RecipeTime {
    FIFTEEN("15분 이하"),
    THIRTY("30분"),
    FORTY_FIVE("45분"),
    SIXTY("60분"),
    NINETY("90분"),
    ONE_TWENTY("120분 이상");

    RecipeTime(String time) {
    }
}
