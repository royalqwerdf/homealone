package com.elice.homealone.member.service;


import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    /**
     * 스프링 시큐리티 인증 로직
     * email을 통해서 SecurityContextHolder에 사용자를 저장해둔다.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = findByEmail(email);
        return member;
    }
    /**
     * email으로 member를 찾는다.
     */
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new HomealoneException(ErrorCode.EMAIL_NOT_FOUND));
    }

    /**
     * 회원 삭제 delete
     */
    public void deleteMember(MemberDTO memberDto) {
        Member findedMember = findByEmail(memberDto.getEmail());
        memberRepository.delete(findedMember);
    }

    /**
     * 회원 탈퇴 withdrawal
     */

    public void withdrawal(MemberDTO memberDTO) {
        Member findedMember = findByEmail(memberDTO.getEmail());
        findedMember.setDeletedAt(true);
        memberRepository.save(findedMember);
    }

}
