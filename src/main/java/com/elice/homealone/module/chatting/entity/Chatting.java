package com.elice.homealone.module.chatting.entity;

import com.elice.homealone.global.common.BaseTimeEntity;
import com.elice.homealone.module.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Entity
@Table(name="chatting")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EnableJpaAuditing
public class Chatting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String chatroomName;

    @ManyToOne
    @JoinColumn(name = "member_sender_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "member_receiver_id")
    private Member receiver;

    @OneToMany(mappedBy = "chatting", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages;


    public ChatDto toDto() {
        return ChatDto.builder()
                .id(this.id)
                .chatroomName(this.chatroomName)
                .senderName(this.sender.getName())
                .receiverName(this.receiver.getName())
                .senderId(this.sender.getId())
                .receiverId(this.receiver.getId())
                .build();
    }

}
