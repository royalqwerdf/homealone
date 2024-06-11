package com.elice.homealone.member.dto.request;


import com.elice.homealone.member.entity.Member;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    private String name;
    private LocalDate birth;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;
    private String firstAddress;
    private String secondAddress;
    private String password;
    public Member toEntity() {
        Member member = new Member();
        member.setName(this.name);
        member.setBirth(this.birth);
        member.setEmail(this.email);
        member.setFirstAddress(this.firstAddress);
        member.setSecondAddress(this.secondAddress);
        member.setPassword(this.password);
        return member;
    }
}
