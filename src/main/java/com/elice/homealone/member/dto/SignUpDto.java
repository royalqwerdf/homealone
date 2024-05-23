package com.elice.homealone.member.dto;


import com.elice.homealone.member.entity.Member;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpDto {
    private String name;
    private LocalDate birth;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;
    private String address;
    private String password;
    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .birth(this.birth)
                .email(this.email)
                .address(this.address)
                .password(this.password)
                .build();
    }
}
