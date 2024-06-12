package com.elice.homealone.module.tag.Repository;

import com.elice.homealone.module.tag.entity.Tag;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String name);
}
