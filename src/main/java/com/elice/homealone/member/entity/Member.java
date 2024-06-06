package com.elice.homealone.member.entity;

import com.elice.homealone.chatting.entity.Chatting;
import com.elice.homealone.comment.entity.Comment;

import com.elice.homealone.global.common.BaseTimeEntity;
import com.elice.homealone.member.dto.MemberDTO;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.postlike.entity.PostLike;
import com.elice.homealone.scrap.entity.Scrap;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "member")
public class Member extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.ROLE_USER;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "deleted_at", nullable = false)
    private boolean deletedAt = false;

    public Member(String email, String password) {
        this.email=email;
        this.password=password;
    }

    public MemberDTO toDto() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(this.id);
        memberDTO.setName(this.name);
        memberDTO.setBirth(this.birth);
        memberDTO.setEmail(this.email);
        memberDTO.setAddress(this.address);
        memberDTO.setImageUrl(this.imageUrl);
        memberDTO.setCreatedAt(this.getCreatedAt());
        memberDTO.setModifiedAt(this.getModifiedAt());
        return memberDTO;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !deletedAt;
    }

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<PostLike> postLikes;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Scrap> scraps;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Chatting> chat_rooms;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments;

}
