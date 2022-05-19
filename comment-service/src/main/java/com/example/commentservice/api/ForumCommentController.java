package com.example.commentservice.api;

import com.example.commentservice.domain.model.entity.ForumComment;
import com.example.commentservice.domain.service.ForumCommentService;
import com.example.commentservice.mapping.ForumCommentMapper;
import com.example.commentservice.models.ForumComment.CreateForumCommentResource;
import com.example.commentservice.models.ForumComment.ForumCommentResource;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/commentservice")
public class ForumCommentController {

    @Autowired
    private ForumCommentService forumcommentService;

    @Autowired
    private ForumCommentMapper mapper;

    @Autowired
    private ModelMapper mapping;

    @GetMapping("/forumcomments")
    public Page<ForumCommentResource> getAllForumComments(Pageable pageable) {
        return mapper.modelListToPage(forumcommentService.getAll(), pageable);
    }

    @GetMapping("/forumcomments/{forumcommentId}")
    public ForumCommentResource getForumCommentById(@PathVariable("forumcommentId") Long forumcommentId) {
        return mapper.toResource(forumcommentService.getById(forumcommentId));
    }

    @CircuitBreaker(name = "userandforumCB", fallbackMethod = "fallBackCreateForumComment")
    @PostMapping("/users/{userId}/forums/{forumId}/forumcomments")
    public ResponseEntity<ForumCommentResource> createForumComment(@PathVariable Long userId, @PathVariable Long forumId, @RequestBody CreateForumCommentResource request) {
        ForumComment forumcomment = mapping.map(request, ForumComment.class);
        return ResponseEntity.ok(mapping.map(forumcommentService.create(userId, forumId, forumcomment), ForumCommentResource.class));
    }

    public ResponseEntity<ForumCommentResource> fallBackCreateForumComment(@PathVariable Long userId, @PathVariable Long forumId, @RequestBody CreateForumCommentResource request, RuntimeException e) {

        return new ResponseEntity("El usuario" + userId+"para el"+"El foro " + forumId + "  no puede crear comentarios", HttpStatus.OK);


    }
    @CircuitBreaker(name = "ForumCB", fallbackMethod = "fallBackGetAllForumCommentsByForumId")
    @GetMapping("/forums/{forumId}/forumcomments")
    public ResponseEntity<Page<ForumCommentResource>> getAllForumCommentsByForumId(@PathVariable Long forumId,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(forumcommentService.getForumCommentByForumId(forumId), pageable));
    }
    public ResponseEntity<Page<ForumCommentResource>> fallBackGetAllForumCommentsByForumId(@PathVariable Long forumId,Pageable pageable, RuntimeException e) {

        return new ResponseEntity("El foro " + forumId + "  no puede retornar comentarios", HttpStatus.OK);

    }

    @DeleteMapping("/forumcomments/{forumcommentId}")
    public ResponseEntity<?> deleteForumComment(@PathVariable Long forumcommentId) {
        return forumcommentService.delete(forumcommentId);
    }


}
