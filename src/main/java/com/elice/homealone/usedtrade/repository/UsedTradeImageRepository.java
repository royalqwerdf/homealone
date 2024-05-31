package com.elice.homealone.usedtrade.repository;

import com.elice.homealone.usedtrade.entity.UsedTradeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedTradeImageRepository extends JpaRepository<UsedTradeImage, Long> {
}
