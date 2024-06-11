package com.elice.homealone.room.service;

import com.elice.homealone.room.repository.RoomViewLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RoomViewLogScheduler {

    private final RoomViewLogRepository roomViewLogRepository;
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanUpOldLogs(){
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        roomViewLogRepository.deleteByTimeStampBefore(oneWeekAgo);
    }
}
