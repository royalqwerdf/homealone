package com.elice.homealone.member.service;


import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.dto.MemberDto;
import com.elice.homealone.member.dto.request.LoginRequestDTO;
import com.elice.homealone.member.dto.request.SignupRequestDTO;
import com.elice.homealone.member.dto.response.LoginResponseDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
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
    public MemberDto signUp(SignupRequestDTO signupRequestDTO){
        String password = passwordEncoder.encode(signupRequestDTO.getPassword());
        Member savedMember = signupRequestDTO.toEntity();
        savedMember.setPassword(password);
        return memberRepository.save(savedMember).toDto();
    }

    /**
     * 로그인
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // 회원 검증
        Member findMember = memberService.findByEmail(loginRequestDTO.getEmail());
        // 비밀번호 검증
        if (passwordEncoder.matches(findMember.getPassword(), loginRequestDTO.getPassword())) {
            String token = jwtTokenProvider.createToken(findMember.getEmail());
            LoginResponseDTO response = new LoginResponseDTO();
            response.setToken(token);
            response.setMessage("로그인이 성공했습니다.");
            return response;
        } else{
            throw new RuntimeException("잘못된 비밀번호입니다.");
        }
    }


    /**
     * Token으로 회원정보를 찾아서 반환
     */
    
//    public MemberDto findMemberByToken

    /**
     * 회원 탈퇴
     */
    public void withdrawal(MemberDto memberDto) {
        Member findedMember = memberService.findByEmail(memberDto.getEmail());
        memberRepository.delete(findedMember);
    }
}

