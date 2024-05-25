package com.elice.homealone.member.repository;


import com.elice.homealone.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByEmail(String email);
    Member findMemberById(Long id);
    Optional<Member> findByEmail(String email);
}





