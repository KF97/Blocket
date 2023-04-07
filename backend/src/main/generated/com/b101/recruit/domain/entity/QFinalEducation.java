package com.b101.recruit.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFinalEducation is a Querydsl query type for FinalEducation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFinalEducation extends EntityPathBase<FinalEducation> {

    private static final long serialVersionUID = -915728364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFinalEducation finalEducation = new QFinalEducation("finalEducation");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath grades = createString("grades");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final QPersonalInfo personalinfo;

    public final StringPath sortation = createString("sortation");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QFinalEducation(String variable) {
        this(FinalEducation.class, forVariable(variable), INITS);
    }

    public QFinalEducation(Path<? extends FinalEducation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFinalEducation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFinalEducation(PathMetadata metadata, PathInits inits) {
        this(FinalEducation.class, metadata, inits);
    }

    public QFinalEducation(Class<? extends FinalEducation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.personalinfo = inits.isInitialized("personalinfo") ? new QPersonalInfo(forProperty("personalinfo"), inits.get("personalinfo")) : null;
    }

}

