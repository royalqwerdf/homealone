package com.elice.homealone.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.elice.homealone.support.domain.support;
import org.springframework.stereotype.Repository;

@Repository
public interface supportRepository extends JpaRepository<support, Long> {
    support save(support spt);
    void deleteAll();
}
