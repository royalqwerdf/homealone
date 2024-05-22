package com.elice.homealone.scrap.entity;

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
@Table(name = "scrap")
public class Scrap {

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
