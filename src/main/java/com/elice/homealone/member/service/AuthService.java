package com.elice.homealone.member.service;


import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.dto.MemberDto;
import com.elice.homealone.member.dto.SignUpDto;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    public Long signUp(SignUpDto signUpDto){
        String password = passwordEncoder.encode(signUpDto.getPassword());
        Member savedMember = signUpDto.toEntity();
        savedMember.setPassword(password);
        return memberRepository.save(savedMember).getId();
    }
//
//    /**
//     * 회원 탈퇴
//     */
//    public void withdrawal(MemberDto memberDto) {
//        Member findedMember = memberRepository
//    }
}
