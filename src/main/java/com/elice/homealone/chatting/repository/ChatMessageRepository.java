package com.elice.homealone.chatting.repository;

import com.elice.homealone.chatting.entity.ChatMessage;
import com.elice.homealone.chatting.entity.MessageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {


    List<MessageDto> findAllChatMessageByChattingIdAndMemberId(Long chatroomId, Long MemberId);
}
