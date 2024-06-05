package com.elice.homealone.recipe.repository.RecipeRepository;

import com.elice.homealone.recipe.entity.QRecipe;
import com.elice.homealone.recipe.entity.Recipe;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QRecipe qRecipe = QRecipe.recipe;

    @Override
    public List<Recipe> findRecipes(Pageable pageable, String userId, String title,
        String description, List<String> tags) {

        // 레시피 엔티티를 선택하고 where을 통해 검색 조건을 적용하여 레시피 리스트를 가져옴
        return jpaQueryFactory
            .selectFrom(qRecipe)
            .where(
                containsTitle(title),
                containsDescription(description)
            )
            .orderBy(getOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanExpression containsTitle(String title) {
        if(title == null) {
            return null;
        }
        return QRecipe.recipe.title.contains(title);
    }

    private BooleanExpression containsDescription(String description) {
        if(description == null) {
            return null;
        }
        return QRecipe.recipe.description.contains(description);
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
        String userId,
        String title,
        String description,
        List<String> tags) {
        return jpaQueryFactory
            .select(qRecipe.count())
            .from(qRecipe)
            .where(
                containsTitle(title),
                containsDescription(description)
            )
            .fetchOne();
    }
    // TODO : userId와 tags에 대한 처리 로직 추가 필요
}
