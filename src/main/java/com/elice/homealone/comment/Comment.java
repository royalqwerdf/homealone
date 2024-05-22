package com.elice.homealone.comment;

import com.elice.homealone.category.entity.Category;
import com.elice.homealone.common.BaseEntity;
import com.elice.homealone.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="create_at", nullable = false)
    private Timestamp createAt;

    @Column(name="modified_at")
    private Timestamp modifiedAt;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @Column(name="post_id", nullable = false)
    private Long postId;
}
