package com.elice.homealone.usedtrade.repository;

import com.elice.homealone.usedtrade.entity.UsedTrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedTradeRepository extends JpaRepository<UsedTrade, Long> {
    Page<UsedTrade> findAll(Pageable pageable);
}
