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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Recipe> findRecipes(Pageable pageable, String userId, String title,
        String description, List<String> tags) {

        // JPA 쿼리 팩토리를 생성하고 실행
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // QueryDSL에서 제공하는 QRecipe 객체를 생성
        QRecipe recipe = QRecipe.recipe;

        // 레시피 엔티티를 선택하고 where을 통해 검색 조건을 적용하여 레시피 리스트를 가져옴
        List<Recipe> recipes = queryFactory
            .selectFrom(recipe)
            .where(
                containsTitle(title),
                containsDescription(description)
            )
            .orderBy(getOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 조건에 부합하는 레시피 엔티티의 수를 가져옴
        Long total = queryFactory
            .select(recipe.count())
            .from(recipe)
            .where(
                containsTitle(title),
                containsDescription(description)
            )
            .fetchOne();

        long totalCount = total != null ? total : 0L;
        return new PageImpl<>(recipes, pageable, totalCount);
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

    // TODO : userId와 tags에 대한 처리 로직 추가 필요
}
