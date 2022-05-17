package com.example.commentservice.api;

import com.example.commentservice.domain.model.entity.ForumComment;
import com.example.commentservice.domain.service.ForumCommentService;
import com.example.commentservice.mapping.ForumCommentMapper;
import com.example.commentservice.models.ForumComment.CreateForumCommentResource;
import com.example.commentservice.models.ForumComment.ForumCommentResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/users/{userId}/forums/{forumId}/forumcomments")
    public ForumCommentResource createForumComment(@PathVariable Long userId, @PathVariable Long forumId, @RequestBody CreateForumCommentResource request) {
        ForumComment forumcomment = mapping.map(request, ForumComment.class);
        return mapping.map(forumcommentService.create(userId, forumId, forumcomment), ForumCommentResource.class);
    }

    @GetMapping("/forums/{forumId}/forumcomments")
    public Page<ForumCommentResource> getAllForumCommentsByForumId(@PathVariable Long forumId,Pageable pageable) {
        return mapper.modelListToPage(forumcommentService.getForumCommentByForumId(forumId), pageable);
    }


    @DeleteMapping("/forumcomments/{forumcommentId}")
    public ResponseEntity<?> deleteForumComment(@PathVariable Long forumcommentId) {
        return forumcommentService.delete(forumcommentId);
    }


}
