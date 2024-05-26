package com.elice.homealone.room.repository;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> , JpaSpecificationExecutor<Room> {
    Optional<Room> findById(Long id);

    Page<Room> findAll(Pageable pageable);






}
