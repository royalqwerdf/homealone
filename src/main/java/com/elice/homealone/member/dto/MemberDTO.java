package com.elice.homealone.member.dto;


import com.elice.homealone.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDTO {
    private Long id;
    private String name;
    private LocalDate birth;
    private String email;
    private String address;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
