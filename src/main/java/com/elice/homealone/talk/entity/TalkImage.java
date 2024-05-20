package com.elice.homealone.talk.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TalkImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String image_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talk_id")
    private Talk talk;
}
