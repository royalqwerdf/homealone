package com.elice.homealone.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.elice.homealone.support.domain.userSupport;

import java.util.List;

public interface userSupportRepository extends JpaRepository<userSupport, Long> {
//    List<userSupport> findByUserId(Long userId);
}
