package com.elice.homealone.module.talk.repository;

import com.elice.homealone.module.tag.entity.PostTag;
import com.elice.homealone.module.talk.entity.Talk;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class TalkSpecification {
    public static Specification<Talk> containsTitle(String title){
        return((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"),"%"+title+"%"));
    }

    public static Specification<Talk> containsPlainContent(String plainContent){
        return (((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("plainContent"),"%"+plainContent+"%")));
    }


    public static Specification<Talk> hasMemberId(Long memberId){
        return (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("memberId"),memberId)));
    }

    public static Specification<Talk> containsTitleOrContent(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("plainContent"), "%" + keyword + "%")
                );
    }
    public static Specification<Talk> containsTag(String tagName) {
        return (root, query, criteriaBuilder) -> {
            // Room과 PostTag를 조인
            Join<Talk, PostTag> tags = root.join("tags", JoinType.INNER);

            // PostTag의 name 필드에 대해 LIKE
            Predicate tagPredicate = criteriaBuilder.like(tags.get("name"), "%" + tagName + "%");

            return tagPredicate;
        };
    }
}
