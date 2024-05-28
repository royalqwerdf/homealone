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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    public SignupResponseDTO signUp(SignupRequestDTO signupRequestDTO){
        SignupResponseDTO response = new SignupResponseDTO();
        try {
            String password = passwordEncoder.encode(signupRequestDTO.getPassword());
            Member savedMember = signupRequestDTO.toEntity();
            savedMember.setPassword(password);
            memberRepository.save(savedMember);
            response.setMessage("회원 가입이 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            response.setMessage("회원 가입 중 오류가 발생하였습니다 :" + e.getMessage());
        }
        return response;
    }

    /**
     * 로그인
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // 회원 검증
        Member findMember = memberService.findByEmail(loginRequestDTO.getEmail());
        // 비밀번호 검증
        if (passwordEncoder.matches(loginRequestDTO.getPassword(), findMember.getPassword())) {
            String acessToken = jwtTokenProvider.createAccessToken(findMember.getEmail());
            String refreshToken = jwtTokenProvider.createRefreshToken(findMember.getEmail());

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
     * AccessToken으로 회원 정보 찾아오기
     */
    public MemberDTO findbyToken(String accessToken) {
        //토큰 유효성 검사
        if (jwtTokenProvider.validateToken(accessToken)) {
            MemberDTO member = memberService.findByEmail(
                    jwtTokenProvider.getEmail(accessToken)
            ).toDto();
            return member;
        } else{
            throw new HomealoneException(ErrorCode.INVALID_TOKEN);
        }
    }


    /**
     * 회원 탈퇴
     */
    public void withdrawal(MemberDTO memberDto) {
        Member findedMember = memberService.findByEmail(memberDto.getEmail());
        memberRepository.delete(findedMember);
    }


}

