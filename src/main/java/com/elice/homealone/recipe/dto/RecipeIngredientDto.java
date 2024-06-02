package com.elice.homealone.recipe.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeIngredientDto {

    private Long id;

    private String name;
    private String quantity;
    private String unit;
    private String note;

    @Builder
    public RecipeIngredientDto(Long id, String name, String quantity, String unit, String note) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.note = note;
    }
}
