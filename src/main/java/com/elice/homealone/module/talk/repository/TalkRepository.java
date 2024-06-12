package com.elice.homealone.module.talk.repository;

import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.talk.entity.Talk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TalkRepository extends JpaRepository<Talk,Long> , JpaSpecificationExecutor<Talk> {
    Optional<Talk> findById(Long id);

    Page<Talk> findAll(Pageable pageable);
    @Query("SELECT t FROM Talk t WHERE t.modifiedAt >= :oneWeekAgo ORDER BY t.view DESC")
    Page<Talk> findTopTalkByView(@Param("oneWeekAgo")LocalDateTime oneWeekAgo, Pageable pageable);


    Page<Talk> findTalkByMember(Member member, Pageable pageable);

}
