package com.elice.homealone.module.room.service;

import com.elice.homealone.module.room.repository.RoomViewLogRepository;
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
        LocalDateTime monthAgo = LocalDateTime.now().withMonth(1);
        roomViewLogRepository.deleteByTimeStampBefore(monthAgo);
    }
}
