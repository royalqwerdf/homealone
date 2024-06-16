package com.elice.homealone.module.member.repository;


import com.elice.homealone.module.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByEmail(String email);
    Member findMemberById(Long id);
    Optional<Member> findByEmail(String email);
    Page<Member> findAll(Pageable pageable);

    Boolean existsByEmail(String email);
}





