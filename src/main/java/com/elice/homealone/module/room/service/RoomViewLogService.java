package com.elice.homealone.module.room.service;

import com.elice.homealone.module.room.entity.Room;
import com.elice.homealone.module.room.entity.RoomViewLog;
import com.elice.homealone.module.room.repository.RoomViewLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RoomViewLogService {
    private final RoomViewLogRepository roomViewLogRepository;
    @Transactional
    public void logView(Room room){
        RoomViewLog roomViewLog = new RoomViewLog(null, room, LocalDateTime.now());
        roomViewLogRepository.save(roomViewLog);
    }
    @Transactional
    public Page<Room> findTopRoomsByViewCountInLastWeek(LocalDateTime localDateTime, Pageable pageable){
        return roomViewLogRepository.findTopRoomsByViewCountInLastWeek(localDateTime,pageable);
    }

}
