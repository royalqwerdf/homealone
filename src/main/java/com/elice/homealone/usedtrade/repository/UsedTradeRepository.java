package com.elice.homealone.usedtrade.repository;

import com.elice.homealone.usedtrade.entity.UsedTrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedTradeRepository extends JpaRepository<UsedTrade, Long> {
    //모든 게시글 조회
    Page<UsedTrade> findAll(Pageable pageable);

    //검색 로직
    @Query("SELECT u FROM UsedTrade u WHERE " +
            "(:title is null OR u.title LIKE %:title%) OR " +
            "(:content is null OR u.content LIKE %:content%) OR" +
            "(:location is null OR u.location LIKE %:location%)"
    )
    Page<UsedTrade> findBySearchQuery(Pageable pageable,String title,String content,String location);

}
