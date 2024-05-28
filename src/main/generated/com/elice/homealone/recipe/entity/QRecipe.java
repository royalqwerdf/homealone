package com.elice.homealone.recipe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipe is a Querydsl query type for Recipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipe extends EntityPathBase<Recipe> {

    private static final long serialVersionUID = 513852034L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipe recipe = new QRecipe("recipe");

    public final com.elice.homealone.post.entity.QPost _super;

    //inherited
    public final ListPath<com.elice.homealone.comment.entity.Comment, com.elice.homealone.comment.entity.QComment> comments;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final EnumPath<com.elice.homealone.recipe.enums.Cuisine> cuisine = createEnum("cuisine", com.elice.homealone.recipe.enums.Cuisine.class);

    public final StringPath description = createString("description");

    public final ListPath<RecipeDetail, QRecipeDetail> details = this.<RecipeDetail, QRecipeDetail>createList("details", RecipeDetail.class, QRecipeDetail.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id;

    public final ListPath<RecipeImage, QRecipeImage> images = this.<RecipeImage, QRecipeImage>createList("images", RecipeImage.class, QRecipeImage.class, PathInits.DIRECT2);

    public final ListPath<RecipeIngredient, QRecipeIngredient> ingredients = this.<RecipeIngredient, QRecipeIngredient>createList("ingredients", RecipeIngredient.class, QRecipeIngredient.class, PathInits.DIRECT2);

    // inherited
    public final com.elice.homealone.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    public final NumberPath<Integer> portions = createNumber("portions", Integer.class);

    //inherited
    public final ListPath<com.elice.homealone.postlike.entity.PostLike, com.elice.homealone.postlike.entity.QPostLike> postLikes;

    public final EnumPath<com.elice.homealone.recipe.enums.RecipeTime> recipeTime = createEnum("recipeTime", com.elice.homealone.recipe.enums.RecipeTime.class);

    public final EnumPath<com.elice.homealone.recipe.enums.RecipeType> recipeType = createEnum("recipeType", com.elice.homealone.recipe.enums.RecipeType.class);

    //inherited
    public final ListPath<com.elice.homealone.scrap.entity.Scrap, com.elice.homealone.scrap.entity.QScrap> scraps;

    //inherited
    public final ListPath<com.elice.homealone.tag.entity.PostTag, com.elice.homealone.tag.entity.QPostTag> tags;

    public final StringPath title = createString("title");

    //inherited
    public final EnumPath<com.elice.homealone.post.entity.Post.Type> type;

    public QRecipe(String variable) {
        this(Recipe.class, forVariable(variable), INITS);
    }

    public QRecipe(Path<? extends Recipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipe(PathMetadata metadata, PathInits inits) {
        this(Recipe.class, metadata, inits);
    }

    public QRecipe(Class<? extends Recipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.elice.homealone.post.entity.QPost(type, metadata, inits);
        this.comments = _super.comments;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.member = _super.member;
        this.modifiedAt = _super.modifiedAt;
        this.postLikes = _super.postLikes;
        this.scraps = _super.scraps;
        this.tags = _super.tags;
        this.type = _super.type;
    }

}

