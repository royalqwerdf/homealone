package com.elice.homealone.usedtrade.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsedTradeImage is a Querydsl query type for UsedTradeImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsedTradeImage extends EntityPathBase<UsedTradeImage> {

    private static final long serialVersionUID = 2010014767L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsedTradeImage usedTradeImage = new QUsedTradeImage("usedTradeImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath main = createBoolean("main");

    public final StringPath url = createString("url");

    public final QUsedTrade usedTrade;

    public QUsedTradeImage(String variable) {
        this(UsedTradeImage.class, forVariable(variable), INITS);
    }

    public QUsedTradeImage(Path<? extends UsedTradeImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsedTradeImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsedTradeImage(PathMetadata metadata, PathInits inits) {
        this(UsedTradeImage.class, metadata, inits);
    }

    public QUsedTradeImage(Class<? extends UsedTradeImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.usedTrade = inits.isInitialized("usedTrade") ? new QUsedTrade(forProperty("usedTrade"), inits.get("usedTrade")) : null;
    }

}

