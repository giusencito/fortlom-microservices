package com.example.forumservice.domain.service;

import com.example.forumservice.domain.model.entity.Forum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.List;
public interface ForumService {
    List<Forum> getAllForums();
    Page<Forum> getAllForums(Pageable pageable);
    Forum getForumById(Long ForumId);
    Forum createForum(Long userId,Forum Forum);
    List<Forum> getForumsByUserId(Long userId);
    ResponseEntity<?> deleteForum(Long forumId);
    boolean existsforum(Long forumId);

}

