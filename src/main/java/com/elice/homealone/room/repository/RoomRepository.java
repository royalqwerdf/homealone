package com.elice.homealone.room.repository;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> , JpaSpecificationExecutor<Room> {
    Optional<Room> findById(Long id);

    Page<Room> findAll(Pageable pageable);

    @Query("SELECT r FROM Room as r WHERE r.createdAt >=  :oneWeekAgo ORDER BY  r.view DESC ")
    Page<Room> findTopRoomByView(@Param("oneWeekAgo")LocalDateTime oneWeekAgo, Pageable pageable);

    Page<Room> findRoomByMember(Member member, Pageable pageable);

}
