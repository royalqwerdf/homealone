package com.elice.homealone.member.entity;

import com.elice.homealone.comment.entity.Comment;
import com.elice.homealone.common.BaseEntity;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.postlike.entity.PostLike;
import com.elice.homealone.scrap.entity.Scrap;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

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

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<PostLike> postLikes;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Scrap> scraps;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments;
}
