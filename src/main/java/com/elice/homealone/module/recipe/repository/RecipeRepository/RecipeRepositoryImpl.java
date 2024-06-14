package com.elice.homealone.module.recipe.repository.RecipeRepository;

import com.elice.homealone.module.recipe.entity.QRecipe;
import com.elice.homealone.module.recipe.entity.Recipe;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QRecipe qRecipe = QRecipe.recipe;

    @Override
    public List<Recipe> findRecipes(
        Pageable pageable,
        String all,
        Long memberId,
        String userName,
        String title,
        String description,
        List<String> tags) {

        BooleanExpression expr;

        if(all != null) {
            expr = containsTitle(all)
                .or(containsDescription(all))
                .or(containsMemberName(all));
        } else {
            expr = containsTitle(title)
                .and(containsDescription(description))
                .and(containsMemberName(userName))
                .and(containsMemberId(memberId));
        }

        // 레시피 엔티티를 선택하고 where을 통해 검색 조건을 적용하여 레시피 리스트를 가져옴
        return jpaQueryFactory
            .selectFrom(qRecipe)
            .where(
                expr != null ? expr : QRecipe.recipe.id.isNotNull()
            )
            .orderBy(getOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanExpression containsTitle(String title) {
        if(title == null) {
            return QRecipe.recipe.id.isNotNull();
        }
        return QRecipe.recipe.title.contains(title);
    }

    private BooleanExpression containsDescription(String description) {
        if(description == null) {
            return QRecipe.recipe.id.isNotNull();
        }

        return QRecipe.recipe.description.contains(description);
    }

    private BooleanExpression containsMemberId(Long memberId) {
        if(memberId == null) {
            return QRecipe.recipe.id.isNotNull();
        }
        return QRecipe.recipe.member.id.eq(memberId);
    }

    private BooleanExpression containsMemberName(String userName) {
        if(userName == null) {
            return QRecipe.recipe.id.isNotNull();
        }
        return QRecipe.recipe.member.name.contains(userName);
    }

    // 정렬을 위한 메소드 (공통으로 뺴야하지 않을까?)
    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for(Sort.Order order : sort) {
            OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
                order.isAscending() ? Order.ASC : Order.DESC,
                Expressions.stringPath(order.getProperty())
            );
            orderSpecifiers.add(orderSpecifier);
        }
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    public Long countRecipes(
        Long memberId,
        String userName,
        String title,
        String description,
        List<String> tags) {
        return jpaQueryFactory
            .select(qRecipe.count())
            .from(qRecipe)
            .where(
                containsTitle(title),
                containsDescription(description),
                containsMemberId(memberId)
            )
            .fetchOne();
    }
}
