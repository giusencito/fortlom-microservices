package com.example.forumservice.domain.persistence;

import com.example.forumservice.domain.model.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository  extends JpaRepository<Forum,Long>{
    List<Forum> findByPersonid(Long userId);
    boolean existsById(Long userId);

}
