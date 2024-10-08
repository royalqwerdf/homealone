package com.elice.homealone.module.talk.repository;

import com.elice.homealone.module.talk.entity.Talk;
import com.elice.homealone.module.talk.entity.TalkViewLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TalkViewLogRepository extends JpaRepository<TalkViewLog, Long> {
    void deleteByTimeStamp(LocalDateTime timeStamp);

    @Query("SELECT t FROM TalkViewLog tv JOIN tv.talk t WHERE tv.timeStamp > :monthAgo GROUP BY t.id ORDER BY COUNT(tv) DESC")
    Page<Talk> findTopTalksByViewCountInLastWeek(@Param("monthAgo") LocalDateTime monthAgo, Pageable pageable);

}
