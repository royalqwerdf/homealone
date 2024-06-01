package com.elice.homealone.member.service;


import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    /**
     * 회원 전체 조회
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    /**
     * 회원 조회
     * email으로 member를 찾는다.
     */
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new HomealoneException(ErrorCode.EMAIL_NOT_FOUND));
    }


    /**
     * 스프링 시큐리티 인증 로직
     * email을 통해서 SecurityContextHolder에 사용자를 저장해둔다.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = findByEmail(email);
        return member;
    }
}
