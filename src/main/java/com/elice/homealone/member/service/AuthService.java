package com.elice.homealone.member.service;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.global.redis.RedisUtil;
import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.dto.request.LoginRequestDTO;
import com.elice.homealone.member.dto.request.SignupRequestDTO;
import com.elice.homealone.member.dto.response.LoginResponseDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private final String GRANT_TYPE = "Bearer ";

    /**
     * 회원 가입
     */
    public void signUp(SignupRequestDTO signupRequestDTO){
        //이메일 중복검사
        emailExists(signupRequestDTO.getEmail());
        //비밀번호 암호화
        String password = passwordEncoder.encode(signupRequestDTO.getPassword());
        Member savedMember = signupRequestDTO.toEntity();
        savedMember.setPassword(password);
        //회원 저장
        memberRepository.save(savedMember);
    }

    /**
     * 로그인
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse httpServletResponse) {
        // 이메일 검증
        Member findMember = memberService.findByEmail(loginRequestDTO.getEmail());
        // 비밀번호 검증
        if (passwordEncoder.matches(loginRequestDTO.getPassword(), findMember.getPassword())) {
            String acessToken = GRANT_TYPE + jwtTokenProvider.createAccessToken(findMember.getEmail());
            String refreshToken = jwtTokenProvider.createRefreshToken(findMember.getEmail()); //쿠키는 공백이 저장되지 않음
            LoginResponseDTO response = new LoginResponseDTO();
            response.setAccessToken(acessToken);
            //refreshToken 쿠키 저장
            httpServletResponse.addCookie(storeRefreshToken(refreshToken));
            return response;
        } else{
            throw new HomealoneException(ErrorCode.MISMATCHED_PASSWORD);
        }
    }

    /**
     * 로그아웃
     */

    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String acccessToken = httpServletRequest.getHeader("Authorization");
        //0. accessToken 검증
        jwtTokenProvider.validateToken(acccessToken);
        //1. accessToken을 블랙리스트 redis에 저장
        String email = jwtTokenProvider.getEmail(acccessToken);
        redisUtil.setBlackList("email", acccessToken);
        //2. refreshToken을 쿠키에서 삭제
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    /**
     * refreshToken 쿠키에 저장
     */
    public Cookie storeRefreshToken(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    /**
     * Token으로 로그인한 회원 정보 조회
     */
    public MemberDTO findLoginMemberByToken(String acccessToken) {
        MemberDTO member = new MemberDTO();
        jwtTokenProvider.validateToken(acccessToken);
        member = memberService.findByEmail(jwtTokenProvider.getEmail(acccessToken)).toDto();
        return member;
    }


    /**
     * 이메일 중복여부 검사
     */
    public void emailExists(String email) {
        if(memberRepository.findByEmail(email).isPresent()){
            throw new HomealoneException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    /**
     * 회원 수정
     * Auth: User
     */
    public Member editMember(Member member, MemberDTO memberDTO) {
        Member changeMember = memberService.findById(member.getId());
        changeMember.setName(memberDTO.getName());
        changeMember.setBirth(memberDTO.getBirth());
        changeMember.setEmail(memberDTO.getEmail());
        changeMember.setAddress(memberDTO.getAddress());
        changeMember.setImageUrl(memberDTO.getImageUrl());
        changeMember.setCreatedAt(memberDTO.getCreatedAt());
        changeMember.setModifiedAt(memberDTO.getModifiedAt());
        return changeMember;
    }

    /**
     * 회원 삭제 delete
     */
    public void deleteMember(Long memberId) {
        Member findedMember = memberService.findById(memberId);
        memberRepository.delete(findedMember);
    }

    /**
     * 회원 탈퇴 withdrawal
     */
    public MemberDTO withdrawal(Member member) {
        Member findedMember = memberService.findByEmail(member.getEmail());
        findedMember.setDeletedAt(true);
        return memberRepository.save(findedMember).toDto();
    }
}

