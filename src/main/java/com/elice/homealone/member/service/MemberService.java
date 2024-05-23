package com.elice.homealone.member.service;


import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;


}
