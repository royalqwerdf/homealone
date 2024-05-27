package com.elice.homealone.talk.repository;

import com.elice.homealone.room.entity.Room;
import com.elice.homealone.talk.entity.Talk;
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
}
