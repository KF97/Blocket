package com.b101.recruit.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserWallet is a Querydsl query type for UserWallet
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserWallet extends EntityPathBase<UserWallet> {

    private static final long serialVersionUID = 426829286L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserWallet userWallet = new QUserWallet("userWallet");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath balance = createString("balance");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath receiving_count = createString("receiving_count");

    public final QUser user;

    public QUserWallet(String variable) {
        this(UserWallet.class, forVariable(variable), INITS);
    }

    public QUserWallet(Path<? extends UserWallet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserWallet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserWallet(PathMetadata metadata, PathInits inits) {
        this(UserWallet.class, metadata, inits);
    }

    public QUserWallet(Class<? extends UserWallet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

