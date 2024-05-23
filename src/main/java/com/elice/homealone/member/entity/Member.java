package com.elice.homealone.member.entity;

import com.elice.homealone.chatting.entity.Chatting;
import com.elice.homealone.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    //enum 사용?
    @Column(name = "role")
    private String role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "ist_deleted", nullable = false)
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Chatting> chat_rooms = new ArrayList<>();

}
