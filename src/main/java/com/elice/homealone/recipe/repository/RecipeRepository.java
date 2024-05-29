package com.elice.homealone.recipe.repository;

import com.elice.homealone.recipe.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r WHERE r.member.id = :memberId")
    Page<Recipe> findByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    Page<Recipe> findByTitleContaining(Pageable pageable, String title);

    Page<Recipe> findByDescriptionContaining(Pageable pageable, String description);
}