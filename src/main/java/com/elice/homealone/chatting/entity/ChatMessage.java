package com.elice.homealone.chatting.entity;




import com.elice.homealone.global.common.BaseTimeEntity;
import com.elice.homealone.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="message")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EnableJpaAuditing
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date sendDate;

    @Column(nullable = false)
    private String sendTime;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private Chatting chatting;

    public MessageDto toDto() {
        return MessageDto.builder()
                .id(this.id)
                .content(this.content)
                .sendDate(this.sendDate)
                .sendTime(this.sendTime)
                .memberId(this.member.getId())
                .chatRoomId(this.chatting.getId())
                .build();
    }
}
