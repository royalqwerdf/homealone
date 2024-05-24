package com.elice.homealone.chatting.entity;

import com.elice.homealone.global.common.BaseEntity;
import com.elice.homealone.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="chatting")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EnableJpaAuditing
public class Chatting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String chatroom_name;

    @Column(nullable = false)
    private String chatUuid;

    @ManyToOne
    @JoinColumn(name = "member_sender_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "member_receiver_id")
    private Member receiver;

    @OneToMany(mappedBy = "chatting")
    private List<ChatMessage> chatMessages = new ArrayList<>();


    public ChatDto toDto() {
        return ChatDto.builder()
                .id(this.id)
                .sender_id(this.sender.getId())
                .receiver_id(this.receiver.getId())
                .build();
    }
}
