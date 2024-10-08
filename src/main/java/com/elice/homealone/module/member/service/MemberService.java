package com.elice.homealone.module.member.service;


import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    /**
     * 회원 전체 조회
     */
    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    /**
     * 회원 조회
     * email으로 member를 찾는다.
     */
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new HomealoneException(ErrorCode.EMAIL_NOT_FOUND));
    }

    /**
     * 회원 조회
     * memberID로 member를 찾는다
     */
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(()->new HomealoneException(ErrorCode.MEMBER_NOT_FOUND));
    }


    /**
     * 스프링 시큐리티 인증 로직
     * email을 통해서 SecurityContextHolder에 사용자를 저장해둔다.
     */
    @Override
    public Member loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = findByEmail(email);
        return member;
    }
}
