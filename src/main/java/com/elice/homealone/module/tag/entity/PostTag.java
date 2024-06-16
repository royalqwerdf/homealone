package com.elice.homealone.module.tag.entity;

import com.elice.homealone.module.post.entity.Post;
import com.elice.homealone.module.tag.dto.PostTagDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @Setter
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    @Setter
    private Tag tag;

    @Builder
    public PostTag(String name) {
        this.name = name;
    }

    public PostTagDto toDto() {
        return PostTagDto.builder()
            .id(this.id)
            .tagName(this.name)
            .build();
    }
}
