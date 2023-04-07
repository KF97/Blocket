package com.b101.recruit.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVerification is a Querydsl query type for Verification
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVerification extends EntityPathBase<Verification> {

    private static final long serialVersionUID = 1120226269L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVerification verification = new QVerification("verification");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath currentStatus = createString("currentStatus");

    public final QGallery gallery;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QPersonalInfo personalinfo;

    public final StringPath reasonsRejection = createString("reasonsRejection");

    public final DateTimePath<java.util.Date> registrationDate = createDateTime("registrationDate", java.util.Date.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QVerification(String variable) {
        this(Verification.class, forVariable(variable), INITS);
    }

    public QVerification(Path<? extends Verification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVerification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVerification(PathMetadata metadata, PathInits inits) {
        this(Verification.class, metadata, inits);
    }

    public QVerification(Class<? extends Verification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gallery = inits.isInitialized("gallery") ? new QGallery(forProperty("gallery")) : null;
        this.personalinfo = inits.isInitialized("personalinfo") ? new QPersonalInfo(forProperty("personalinfo"), inits.get("personalinfo")) : null;
    }

}

