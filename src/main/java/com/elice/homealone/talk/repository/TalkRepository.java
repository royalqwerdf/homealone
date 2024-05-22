package com.elice.homealone.talk.repository;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.talk.entity.Talk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.Optional;

public interface TalkRepository extends JpaRepository<Talk,Long> {
    Optional<Talk> findById(Long id);

    Page<Talk> findAll(Pageable pageable);

    Page<Talk> findByMember(Member member, Pageable pageable);

    Page<Talk> searchByTitleContainingORContentContaining(String titleQuery,String contentQuery Pageable pageable);
}
