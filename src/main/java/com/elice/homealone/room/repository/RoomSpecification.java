package com.elice.homealone.room.repository;

import com.elice.homealone.room.entity.Room;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import  com.elice.homealone.tag.entity.PostTag;

public class RoomSpecification {
    public static Specification<Room> containsTitle(String title){
        return((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"),"%"+title+"%"));
    }

    public static Specification<Room> containsPlainContent(String plainContent){
        return (((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("plainContent"),"%"+plainContent+"%")));
    }


    public static Specification<Room> hasMemberId(Long memberId){
        return (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("memberId"),memberId)));
    }

    public static Specification<Room> containsTitleOrContent(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("plainContent"), "%" + keyword + "%")
                );
    }

    public static Specification<Room> containsTag(String tagName) {
        return (root, query, criteriaBuilder) -> {
            // Room과 PostTag를 조인
            Join<Room, PostTag> tags = root.join("tags", JoinType.INNER);

            // PostTag의 name 필드에 대해 LIKE
            Predicate tagPredicate = criteriaBuilder.like(tags.get("name"), "%" + tagName + "%");

            return tagPredicate;
        };
    }
}
