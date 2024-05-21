package com.elice.homealone.like.entity;

import com.elice.homealone.category.entity.Category;
import com.elice.homealone.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "like") // 테이블명 수정
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @Column(name="post_id", nullable = false)
    private Long postId;

    @Column(name="post_title", nullable = false)
    private String postTitle;

}
