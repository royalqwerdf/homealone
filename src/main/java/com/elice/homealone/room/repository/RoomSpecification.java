package com.elice.homealone.room.repository;

import com.elice.homealone.room.entity.Room;
import org.springframework.data.jpa.domain.Specification;

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
}
