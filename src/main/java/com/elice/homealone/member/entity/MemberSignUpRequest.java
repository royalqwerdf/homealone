package com.elice.homealone.member.entity;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MemberSignUpRequest {
    private String name;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;
    private String address;
    private String phone;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .address(this.address)
                .phone(this.phone)
                .password(this.password)
                .build();
    }
}
