package com.elice.homealone.talk.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTalk is a Querydsl query type for Talk
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTalk extends EntityPathBase<Talk> {

    private static final long serialVersionUID = -67723522L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTalk talk = new QTalk("talk");

    public final com.elice.homealone.post.entity.QPost _super;

    //inherited
    public final ListPath<com.elice.homealone.comment.entity.Comment, com.elice.homealone.comment.entity.QComment> comments;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final com.elice.homealone.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    //inherited
    public final ListPath<com.elice.homealone.postlike.entity.PostLike, com.elice.homealone.postlike.entity.QPostLike> postLikes;

    //inherited
    public final ListPath<com.elice.homealone.scrap.entity.Scrap, com.elice.homealone.scrap.entity.QScrap> scraps;

    //inherited
    public final ListPath<com.elice.homealone.tag.entity.PostTag, com.elice.homealone.tag.entity.QPostTag> tags;

    public final ListPath<TalkImage, QTalkImage> talkImages = this.<TalkImage, QTalkImage>createList("talkImages", TalkImage.class, QTalkImage.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final EnumPath<com.elice.homealone.post.entity.Post.Type> type;

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QTalk(String variable) {
        this(Talk.class, forVariable(variable), INITS);
    }

    public QTalk(Path<? extends Talk> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTalk(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTalk(PathMetadata metadata, PathInits inits) {
        this(Talk.class, metadata, inits);
    }

    public QTalk(Class<? extends Talk> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.elice.homealone.post.entity.QPost(type, metadata, inits);
        this.comments = _super.comments;
        this.createdAt = _super.createdAt;
        this.member = _super.member;
        this.modifiedAt = _super.modifiedAt;
        this.postLikes = _super.postLikes;
        this.scraps = _super.scraps;
        this.tags = _super.tags;
        this.type = _super.type;
    }

}

