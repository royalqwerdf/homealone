package com.elice.homealone.like.repository;

import com.elice.homealone.like.entity.QLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QLike qLike = QLike.like;

}
