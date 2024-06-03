package com.elice.homealone.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomImage is a Querydsl query type for RoomImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomImage extends EntityPathBase<RoomImage> {

    private static final long serialVersionUID = 215054815L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomImage roomImage = new QRoomImage("roomImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image_url = createString("image_url");

    public final QRoom room;

    public QRoomImage(String variable) {
        this(RoomImage.class, forVariable(variable), INITS);
    }

    public QRoomImage(Path<? extends RoomImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomImage(PathMetadata metadata, PathInits inits) {
        this(RoomImage.class, metadata, inits);
    }

    public QRoomImage(Class<? extends RoomImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
    }

}

