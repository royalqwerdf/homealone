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

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService{
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
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
     * 회원 수정
     * Auth: User
     */
    public Member editMember(MemberDTO memberDTO, String accessToken) {
        //토큰 유효성 검사
        if (jwtTokenProvider.validateToken(accessToken)) {
            Member member = authService.findbyToken(accessToken);
            member.setName(memberDTO.getName());
            member.setBirth(memberDTO.getBirth());
            member.setEmail(memberDTO.getEmail());
            member.setAddress(memberDTO.getAddress());
            member.setImageUrl(memberDTO.getImageUrl());
            member.setCreatedAt(memberDTO.getCreatedAt());
            member.setModifiedAt(memberDTO.getModifiedAt());
            return member;
        }else {
            throw new HomealoneException(ErrorCode.INVALID_TOKEN);
        }

    }
    
    /**
     * 회원 삭제 delete
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteMember(MemberDTO memberDto, String accessToken) {

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
