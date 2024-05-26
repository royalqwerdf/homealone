package com.elice.homealone.member.dto;


import com.elice.homealone.member.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
public class MemberDto{
    private String name;
    private LocalDate birth;
    private String email;
    private String address;
    private String imageUrl;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String token;
    private boolean deletedAt;
}
