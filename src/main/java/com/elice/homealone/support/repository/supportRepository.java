package com.elice.homealone.support.repository;

import com.google.api.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.elice.homealone.support.domain.support;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.elice.homealone.support.dto.supportDTO;

import java.util.Optional;

@Repository
public interface supportRepository extends PagingAndSortingRepository<support, Long> {

    void deleteAll();

    void save(support entity);

    Optional<support> findById(Long id);
}
