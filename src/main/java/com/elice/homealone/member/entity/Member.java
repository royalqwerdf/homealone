package com.elice.homealone.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "member")
public class Member{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    //Enum 사용?
    @Column(name = "role")
    private String role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "ist_deleted", nullable = false)
    private boolean isDeleted = false;

}
