package com.elice.homealone.module.usedtrade.repository;

import com.elice.homealone.module.usedtrade.entity.UsedTradeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedTradeImageRepository extends JpaRepository<UsedTradeImage, Long> {
}
