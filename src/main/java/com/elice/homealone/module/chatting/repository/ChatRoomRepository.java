package com.elice.homealone.module.chatting.repository;

import com.elice.homealone.module.chatting.entity.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<Chatting, Long> {

    List<Chatting> findAllChattingBySenderId(Long memberId);

    Chatting findChattingById(Long chatroomId);
}
