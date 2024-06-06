package com.elice.homealone.recipe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipeDetail is a Querydsl query type for RecipeDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipeDetail extends EntityPathBase<RecipeDetail> {

    private static final long serialVersionUID = 1983929715L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipeDetail recipeDetail = new QRecipeDetail("recipeDetail");

    public final com.elice.homealone.global.common.QBaseTimeEntity _super = new com.elice.homealone.global.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QRecipe recipe;

    public QRecipeDetail(String variable) {
        this(RecipeDetail.class, forVariable(variable), INITS);
    }

    public QRecipeDetail(Path<? extends RecipeDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipeDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipeDetail(PathMetadata metadata, PathInits inits) {
        this(RecipeDetail.class, metadata, inits);
    }

    public QRecipeDetail(Class<? extends RecipeDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipe = inits.isInitialized("recipe") ? new QRecipe(forProperty("recipe")) : null;
    }

}

