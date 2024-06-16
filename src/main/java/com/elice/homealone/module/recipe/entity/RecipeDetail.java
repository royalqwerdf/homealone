package com.elice.homealone.module.recipe.entity;

import com.elice.homealone.global.common.BaseTimeEntity;
import com.elice.homealone.module.recipe.dto.RecipeDetailDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecipeDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 1500)
    private String description;

    @Column
    private String fileName;

    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @Setter
    private Recipe recipe;

    @Builder
    public RecipeDetail(String description, String fileName ,String imageUrl){
        this.description = description;
        this.fileName = fileName;
        this.imageUrl = imageUrl;
    }

    @Builder
    public RecipeDetail(String description){
        this.description = description;
    }

    public RecipeDetailDto toDto() {
        return  RecipeDetailDto.builder()
            .id(this.id)
            .description(this.description)
            .fileName(this.fileName)
            .imageUrl(this.imageUrl)
            .build();
    }
}
