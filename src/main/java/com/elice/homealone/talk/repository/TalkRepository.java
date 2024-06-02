package com.elice.homealone.talk.repository;

import com.elice.homealone.talk.entity.Talk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.awt.*;
import java.util.Optional;

public interface TalkRepository extends JpaRepository<Talk,Long> , JpaSpecificationExecutor<Talk> {
    Optional<Talk> findById(Long id);

    Page<Talk> findAll(Pageable pageable);

}
