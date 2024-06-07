package com.elice.homealone.talk.repository;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.talk.entity.Talk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TalkRepository extends JpaRepository<Talk,Long> , JpaSpecificationExecutor<Talk> {
    Optional<Talk> findById(Long id);

    Page<Talk> findAll(Pageable pageable);
    @Query("SELECT r from Room r WHERE r.createdAt >= :oneWeekAgo ORDER BY r.view DESC ")
    Page<Talk> findTopTalkByView(@Param("oneWeekAgo")LocalDateTime oneWeekAgo, Pageable pageable);


    Page<Talk> findTalkByMember(Member member, Pageable pageable);

}
