package com.elice.homealone.talk.Service;

import com.elice.homealone.talk.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class TalkService {
    private final TalkRepository talkRepository;
}
