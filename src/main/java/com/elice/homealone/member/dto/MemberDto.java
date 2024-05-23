package com.elice.homealone.member.dto;


import com.elice.homealone.member.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {
    private String name;
    private LocalDate birth;
    private String email;
    private String address;
    private String imageUrl;
    private Role role;
}
