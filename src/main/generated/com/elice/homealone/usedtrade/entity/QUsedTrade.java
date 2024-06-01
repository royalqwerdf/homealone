package com.elice.homealone.usedtrade.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsedTrade is a Querydsl query type for UsedTrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsedTrade extends EntityPathBase<UsedTrade> {

    private static final long serialVersionUID = 855592076L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsedTrade usedTrade = new QUsedTrade("usedTrade");

    public final com.elice.homealone.post.entity.QPost _super;

    //inherited
    public final ListPath<com.elice.homealone.comment.entity.Comment, com.elice.homealone.comment.entity.QComment> comments;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final ListPath<UsedTradeImage, QUsedTradeImage> images = this.<UsedTradeImage, QUsedTradeImage>createList("images", UsedTradeImage.class, QUsedTradeImage.class, PathInits.DIRECT2);

    public final StringPath location = createString("location");

    // inherited
    public final com.elice.homealone.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    //inherited
    public final ListPath<com.elice.homealone.postlike.entity.PostLike, com.elice.homealone.postlike.entity.QPostLike> postLikes;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final ListPath<com.elice.homealone.scrap.entity.Scrap, com.elice.homealone.scrap.entity.QScrap> scraps;

    //inherited
    public final ListPath<com.elice.homealone.tag.entity.PostTag, com.elice.homealone.tag.entity.QPostTag> tags;

    public final StringPath title = createString("title");

    //inherited
    public final EnumPath<com.elice.homealone.post.entity.Post.Type> type;

    public QUsedTrade(String variable) {
        this(UsedTrade.class, forVariable(variable), INITS);
    }

    public QUsedTrade(Path<? extends UsedTrade> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsedTrade(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsedTrade(PathMetadata metadata, PathInits inits) {
        this(UsedTrade.class, metadata, inits);
    }

    public QUsedTrade(Class<? extends UsedTrade> type, PathMetadata metadata, PathInits inits) {
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

