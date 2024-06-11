package com.elice.homealone.talk.repository;

import com.elice.homealone.room.entity.Room;
import com.elice.homealone.room.repository.RoomViewLogRepository;
import com.elice.homealone.talk.entity.Talk;
import com.elice.homealone.talk.entity.TalkViewLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TalkViewLogRepository extends JpaRepository<TalkViewLog, Long> {
    void deleteByTimeStamp(LocalDateTime timeStamp);

    @Query("SELECT t FROM TalkViewLog tv JOIN Talk  t WHERE tv.timeStamp >:oneWeekAgo GROUP BY t ORDER BY COUNT(tv) DESC ")
    Page<Talk> findTopTalksByViewCountInLastWeek(@Param("oneWeekAgo") LocalDateTime oneWeekAgo, Pageable pageable);

}
