package com.example.commentservice.domain.persistence;

import com.example.commentservice.domain.model.entity.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumCommentRepository extends JpaRepository<ForumComment,Long>{
    List<ForumComment> findByForumid(Long forumId);
    List<ForumComment> findByPersonid (Long UserId);
}
