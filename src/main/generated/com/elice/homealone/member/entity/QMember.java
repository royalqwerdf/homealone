package com.elice.homealone.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -404658278L;

    public static final QMember member = new QMember("member1");

    public final com.elice.homealone.global.common.QBaseTimeEntity _super = new com.elice.homealone.global.common.QBaseTimeEntity(this);

    public final StringPath address = createString("address");

    public final DatePath<java.time.LocalDate> birth = createDate("birth", java.time.LocalDate.class);

    public final ListPath<com.elice.homealone.chatting.entity.Chatting, com.elice.homealone.chatting.entity.QChatting> chat_rooms = this.<com.elice.homealone.chatting.entity.Chatting, com.elice.homealone.chatting.entity.QChatting>createList("chat_rooms", com.elice.homealone.chatting.entity.Chatting.class, com.elice.homealone.chatting.entity.QChatting.class, PathInits.DIRECT2);

    public final ListPath<com.elice.homealone.comment.entity.Comment, com.elice.homealone.comment.entity.QComment> comments = this.<com.elice.homealone.comment.entity.Comment, com.elice.homealone.comment.entity.QComment>createList("comments", com.elice.homealone.comment.entity.Comment.class, com.elice.homealone.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deletedAt = createBoolean("deletedAt");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final ListPath<com.elice.homealone.postlike.entity.PostLike, com.elice.homealone.postlike.entity.QPostLike> postLikes = this.<com.elice.homealone.postlike.entity.PostLike, com.elice.homealone.postlike.entity.QPostLike>createList("postLikes", com.elice.homealone.postlike.entity.PostLike.class, com.elice.homealone.postlike.entity.QPostLike.class, PathInits.DIRECT2);

    public final ListPath<com.elice.homealone.post.entity.Post, com.elice.homealone.post.entity.QPost> posts = this.<com.elice.homealone.post.entity.Post, com.elice.homealone.post.entity.QPost>createList("posts", com.elice.homealone.post.entity.Post.class, com.elice.homealone.post.entity.QPost.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final ListPath<com.elice.homealone.scrap.entity.Scrap, com.elice.homealone.scrap.entity.QScrap> scraps = this.<com.elice.homealone.scrap.entity.Scrap, com.elice.homealone.scrap.entity.QScrap>createList("scraps", com.elice.homealone.scrap.entity.Scrap.class, com.elice.homealone.scrap.entity.QScrap.class, PathInits.DIRECT2);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

