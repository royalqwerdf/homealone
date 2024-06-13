package com.elice.homealone.module.room.repository;

import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> , JpaSpecificationExecutor<Room> {
    Optional<Room> findById(Long id);

    Page<Room> findAll(Pageable pageable);

    Page<Room> findRoomByMember(Member member, Pageable pageable);
    List<Room> findTop4ByOrderByCreatedAtDesc();


}
