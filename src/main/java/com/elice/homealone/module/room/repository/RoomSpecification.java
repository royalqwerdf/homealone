package com.elice.homealone.module.room.repository;

import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.room.entity.Room;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.elice.homealone.module.tag.entity.PostTag;

public class RoomSpecification {
    public static Specification<Room> containsTitle(String title){
        return((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"),"%"+title+"%"));
    }

    public static Specification<Room> containsPlainContent(String plainContent){
        return (((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("plainContent"),"%"+plainContent+"%")));
    }


    public static Specification<Room> hasMemberName(String memberName){
        return (((root, query, criteriaBuilder) -> {
            Join<Room, Member> roomMemberJoin = root.join("member", JoinType.INNER);
            Predicate roomByMemberName = criteriaBuilder.equal(roomMemberJoin.get("name"), memberName);
            return roomByMemberName;
        }));
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
