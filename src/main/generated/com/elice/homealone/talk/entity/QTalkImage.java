package com.elice.homealone.talk.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTalkImage is a Querydsl query type for TalkImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTalkImage extends EntityPathBase<TalkImage> {

    private static final long serialVersionUID = 1629669629L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTalkImage talkImage = new QTalkImage("talkImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image_url = createString("image_url");

    public final QTalk talk;

    public QTalkImage(String variable) {
        this(TalkImage.class, forVariable(variable), INITS);
    }

    public QTalkImage(Path<? extends TalkImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTalkImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTalkImage(PathMetadata metadata, PathInits inits) {
        this(TalkImage.class, metadata, inits);
    }

    public QTalkImage(Class<? extends TalkImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.talk = inits.isInitialized("talk") ? new QTalk(forProperty("talk"), inits.get("talk")) : null;
    }

}

