package com.b101.recruit.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonalInfo is a Querydsl query type for PersonalInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPersonalInfo extends EntityPathBase<PersonalInfo> {

    private static final long serialVersionUID = -1949346608L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonalInfo personalInfo = new QPersonalInfo("personalInfo");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath address = createString("address");

    public final DatePath<java.sql.Date> dateBirth = createDate("dateBirth", java.sql.Date.class);

    public final StringPath disabled = createString("disabled");

    public final StringPath englishName = createString("englishName");

    public final StringPath gender = createString("gender");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath militaryService = createString("militaryService");

    public final QUser user;

    public final StringPath veteransAffairs = createString("veteransAffairs");

    public QPersonalInfo(String variable) {
        this(PersonalInfo.class, forVariable(variable), INITS);
    }

    public QPersonalInfo(Path<? extends PersonalInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonalInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonalInfo(PathMetadata metadata, PathInits inits) {
        this(PersonalInfo.class, metadata, inits);
    }

    public QPersonalInfo(Class<? extends PersonalInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

