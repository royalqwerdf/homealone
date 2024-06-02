package com.elice.homealone.recipe.entity;

import com.elice.homealone.global.common.BaseTimeEntity;
import com.elice.homealone.recipe.dto.RecipeImageDto;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@Table(indexes = {@Index(name = "index_fileName", columnList = "fileName")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecipeImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String fileName;

    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @Setter
    private Recipe recipe;

    @Builder
    public RecipeImage(String imageUrl, String fileName) {
        this.imageUrl = imageUrl;
        this.fileName = fileName;
    }

    public RecipeImageDto toDto() {
        return RecipeImageDto.builder()
            .id(this.id)
            .fileName(this.fileName)
            .url(this.imageUrl)
            .build();
    }
}
