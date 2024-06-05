package com.elice.homealone.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = -1519294244L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoom room = new QRoom("room");

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

    public final StringPath plainContent = createString("plainContent");

    //inherited
    public final ListPath<com.elice.homealone.postlike.entity.PostLike, com.elice.homealone.postlike.entity.QPostLike> postLikes;

    public final ListPath<RoomImage, QRoomImage> roomImages = this.<RoomImage, QRoomImage>createList("roomImages", RoomImage.class, QRoomImage.class, PathInits.DIRECT2);

    //inherited
    public final ListPath<com.elice.homealone.scrap.entity.Scrap, com.elice.homealone.scrap.entity.QScrap> scraps;

    //inherited
    public final ListPath<com.elice.homealone.tag.entity.PostTag, com.elice.homealone.tag.entity.QPostTag> tags;

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    //inherited
    public final EnumPath<com.elice.homealone.post.entity.Post.Type> type;

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QRoom(String variable) {
        this(Room.class, forVariable(variable), INITS);
    }

    public QRoom(Path<? extends Room> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoom(PathMetadata metadata, PathInits inits) {
        this(Room.class, metadata, inits);
    }

    public QRoom(Class<? extends Room> type, PathMetadata metadata, PathInits inits) {
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

