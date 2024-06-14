package com.elice.homealone.support.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user_support")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class userSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "support_id")
    private support support;

    public userSupport(support support) {
        this.support = support;
    }
}
