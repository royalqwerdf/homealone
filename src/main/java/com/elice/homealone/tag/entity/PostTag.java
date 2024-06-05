package com.elice.homealone.tag.entity;

import com.elice.homealone.post.entity.Post;
import com.elice.homealone.tag.dto.PostTagDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Setter
    private Post post;

    @ManyToOne
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
