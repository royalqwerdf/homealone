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





    /**
     * 이메일 중복여부 검사
     */
    public void emailExists(String email) {
        if(memberRepository.findByEmail(email).isPresent()){
            throw new HomealoneException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }


}

