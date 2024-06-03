package com.elice.homealone.member.service;


import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.member.dto.request.LoginRequestDTO;
import com.elice.homealone.member.dto.request.SignupRequestDTO;
import com.elice.homealone.member.dto.response.LoginResponseDTO;
import com.elice.homealone.member.dto.response.SignupResponseDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final String GRANT_TYPE = "Bearer ";

    /**
     * 회원 가입
     */
    //exception 잡는 로직을 서비스단에서 던질지 컨트롤러단에서 던질지고민
    //회원가입은 서비스에서 오류를 안던지고 로그인은 서비스에서 오류를 던짐;;
    public SignupResponseDTO signUp(SignupRequestDTO signupRequestDTO){
        SignupResponseDTO response = new SignupResponseDTO();
        //이메일 중복검사
        emailExists(signupRequestDTO.getEmail());
        //비밀번호 암호화
        String password = passwordEncoder.encode(signupRequestDTO.getPassword());
        Member savedMember = signupRequestDTO.toEntity();
        savedMember.setPassword(password);
        //회원 저장
        memberRepository.save(savedMember);
        response.setMessage("회원 가입이 성공적으로 완료되었습니다.");
        return response;
    }

    /**
     * 로그인
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // 이메일 검증
        Member findMember = memberService.findByEmail(loginRequestDTO.getEmail());
        // 비밀번호 검증
        if (passwordEncoder.matches(loginRequestDTO.getPassword(), findMember.getPassword())) {
            String acessToken = GRANT_TYPE + jwtTokenProvider.createAccessToken(findMember.getEmail());
            String refreshToken = GRANT_TYPE + jwtTokenProvider.createRefreshToken(findMember.getEmail());
            LoginResponseDTO response = new LoginResponseDTO();
            response.setAccessToken(acessToken);
            response.setMessage("로그인이 성공했습니다.");
            storeRefreshToken(refreshToken);
            return response;
        } else{
            throw new HomealoneException(ErrorCode.MISMATCHED_PASSWORD);
        }
    }

    /**
     * refreshToken 쿠키에 저장
     */
    public void storeRefreshToken(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
    }

    /**
     * Token으로 로그인한 회원 정보 조회
     */
    public Member findLoginMemberByToken(String token) {
        //토큰 유효성 검사
        if (jwtTokenProvider.validateToken(token)) {
            Member member = memberService.findByEmail(jwtTokenProvider.getEmail(token));
            return member;
        } else{
            throw new HomealoneException(ErrorCode.INVALID_TOKEN);
        }
    }

//    public MemberDto findMemberByToken

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
    public Member editMember(MemberDTO memberDTO, String accessToken) {
        //토큰 유효성 검사
        if (jwtTokenProvider.validateToken(accessToken)) {
            Member member = findLoginMemberByToken(accessToken);
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
    public void deleteMember(Long memberId, String accessToken) {
        Member findedMember = memberService.findById(memberId);
        memberRepository.delete(findedMember);
    }

    /**
     * 회원 탈퇴 withdrawal
     */
    public Long withdrawal(MemberDTO memberDTO) {
        Member findedMember = memberService.findByEmail(memberDTO.getEmail());
        findedMember.setDeletedAt(true);
        memberRepository.save(findedMember);
        return findedMember.getId();
    }


}

