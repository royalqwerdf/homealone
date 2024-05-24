package com.elice.homealone.usedtrade.entity;

import com.elice.homealone.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UsedTrade extends Post {

    @Column(name="title")
    private String title;

    @Column(name="price")
    private int price;

    @Column(name="location")
    private String location;

    @Column(name="content")
    private String content;

    @OneToMany(mappedBy = "usedTrade")
    private List<UsedTradeImage> images = new ArrayList<>();

}
