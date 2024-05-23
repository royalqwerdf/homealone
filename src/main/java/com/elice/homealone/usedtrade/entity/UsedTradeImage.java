package com.elice.homealone.usedtrade.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsedTradeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="main")
    private boolean main;

    @Column(name="url")
    private String url;

    @ManyToOne
    @JoinColumn(name="used_trade_id")
    private UsedTrade usedTrade;
}
