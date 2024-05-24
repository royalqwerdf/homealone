package com.elice.homealone.room.repository;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.talk.entity.Talk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    Optional<Room> findById(Long id);

    Page<Room> findAll(Pageable pageable);

    Page<Room> findByMember(Member member, Pageable pageable);

    //Page<Room> searchByTitleContainingOrContentContaining(String titleQuery,String contentQuery, Pageable pageable);


}
