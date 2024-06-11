package com.elice.homealone.room.repository;

import com.elice.homealone.room.entity.Room;
import com.elice.homealone.room.entity.RoomViewLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface RoomViewLogRepository extends JpaRepository<RoomViewLog,Long> {
    List<RoomViewLogRepository> findByTimeStampBefore(LocalDateTime dateTime);
    void deleteByTimeStampBefore(LocalDateTime dateTime);

    @Query("SELECT r FROM RoomViewLog rv Join rv.room r WHERE rv.timeStamp >= :oneWeekAgo GROUP BY r ORDER BY COUNT(rv) DESC ")
    Page<Room> findTopRoomsByViewCountInLastWeek(@Param("oneWeekAgo") LocalDateTime oneWeekAgo, Pageable pageable);

}
