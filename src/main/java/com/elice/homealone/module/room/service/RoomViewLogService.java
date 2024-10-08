package com.elice.homealone.module.room.service;

import com.elice.homealone.module.room.entity.Room;
import com.elice.homealone.module.room.entity.RoomViewLog;
import com.elice.homealone.module.room.repository.RoomViewLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

    public Page<Room> findTop4RoomsByViewCountInLastWeek(LocalDateTime localDateTime,Pageable pageable){
        // 서비스나 호출하는 곳에서
        Page<Room> topRooms = roomViewLogRepository.findTopRoomsByViewCountInLastWeek(localDateTime, pageable);

        return topRooms;

    }

}
