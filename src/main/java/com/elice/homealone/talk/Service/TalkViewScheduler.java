package com.elice.homealone.talk.Service;

import com.elice.homealone.talk.repository.TalkViewLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TalkViewScheduler {
    private final TalkViewLogRepository talkViewLogRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanUpOldLogs(){
        LocalDateTime localDateTime = LocalDateTime.now().minusWeeks(1);
        talkViewLogRepository.deleteByTimeStamp(localDateTime);
    }
}
