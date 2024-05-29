package com.elice.homealone.member.dto.request;


import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.entity.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class SignupRequestDTO {
    private String name;
    private LocalDate birth;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;
    private String address;
    private String password;
    public Member toEntity() {
        Member member = new Member();
        member.setName(this.name);
        member.setBirth(this.birth);
        member.setEmail(this.email);
        member.setAddress(this.address);
        member.setPassword(this.password);
        return member;
    }
}
