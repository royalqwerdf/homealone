package com.elice.homealone.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -279373594L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.elice.homealone.global.common.QBaseTimeEntity _super = new com.elice.homealone.global.common.QBaseTimeEntity(this);

    public final ListPath<com.elice.homealone.comment.entity.Comment, com.elice.homealone.comment.entity.QComment> comments = this.<com.elice.homealone.comment.entity.Comment, com.elice.homealone.comment.entity.QComment>createList("comments", com.elice.homealone.comment.entity.Comment.class, com.elice.homealone.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.elice.homealone.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<com.elice.homealone.postlike.entity.PostLike, com.elice.homealone.postlike.entity.QPostLike> postLikes = this.<com.elice.homealone.postlike.entity.PostLike, com.elice.homealone.postlike.entity.QPostLike>createList("postLikes", com.elice.homealone.postlike.entity.PostLike.class, com.elice.homealone.postlike.entity.QPostLike.class, PathInits.DIRECT2);

    public final ListPath<com.elice.homealone.scrap.entity.Scrap, com.elice.homealone.scrap.entity.QScrap> scraps = this.<com.elice.homealone.scrap.entity.Scrap, com.elice.homealone.scrap.entity.QScrap>createList("scraps", com.elice.homealone.scrap.entity.Scrap.class, com.elice.homealone.scrap.entity.QScrap.class, PathInits.DIRECT2);

    public final ListPath<com.elice.homealone.tag.entity.PostTag, com.elice.homealone.tag.entity.QPostTag> tags = this.<com.elice.homealone.tag.entity.PostTag, com.elice.homealone.tag.entity.QPostTag>createList("tags", com.elice.homealone.tag.entity.PostTag.class, com.elice.homealone.tag.entity.QPostTag.class, PathInits.DIRECT2);

    public final EnumPath<Post.Type> type = createEnum("type", Post.Type.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.elice.homealone.member.entity.QMember(forProperty("member")) : null;
    }

}

