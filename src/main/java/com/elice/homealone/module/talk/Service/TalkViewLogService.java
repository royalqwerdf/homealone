package com.elice.homealone.module.talk.Service;

import com.elice.homealone.module.talk.entity.Talk;
import com.elice.homealone.module.talk.entity.TalkViewLog;
import com.elice.homealone.module.talk.repository.TalkViewLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TalkViewLogService {
    private final TalkViewLogRepository talkViewLogRepository;

    @Transactional
    public void logView(Talk talk){
        TalkViewLog talkViewLog = new TalkViewLog(null, talk, LocalDateTime.now());
        talkViewLogRepository.save(talkViewLog);
    }
    @Transactional
    public Page<Talk> findTopTalksByViewCountInLastWeek(LocalDateTime oneWeekAgo, Pageable pageable){
        return talkViewLogRepository.findTopTalksByViewCountInLastWeek(oneWeekAgo,pageable);
    }

}
