package com.elice.homealone.talk.Service;

import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.talk.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class TalkService {
    private final TalkRepository talkRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
}
