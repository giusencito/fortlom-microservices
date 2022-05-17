package com.example.commentservice.api;


import com.example.commentservice.domain.model.entity.ForumComment;
import com.example.commentservice.domain.model.entity.PublicationComment;
import com.example.commentservice.domain.service.ForumCommentService;
import com.example.commentservice.domain.service.PublicationCommentService;
import com.example.commentservice.mapping.ForumCommentMapper;
import com.example.commentservice.mapping.PublicationCommentMapper;
import com.example.commentservice.models.ForumComment.CreateForumCommentResource;
import com.example.commentservice.models.ForumComment.ForumCommentResource;
import com.example.commentservice.models.PublicationComment.CreatePublicationComment;
import com.example.commentservice.models.PublicationComment.PublicationCommentResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {


    @Autowired
    private ForumCommentService forumcommentService;

    @Autowired
    private ForumCommentMapper forumCommentMapper;

    @Autowired
    private PublicationCommentService publicationCommentService;

    @Autowired
    private PublicationCommentMapper publicationCommentMapper;

    @Autowired
    private ModelMapper mapping;


    @GetMapping("forumcomments")
    public Page<ForumCommentResource> getAllForumComments(Pageable pageable) {
        return forumCommentMapper.modelListToPage(forumcommentService.getAll(), pageable);
    }

    @GetMapping("forumcomments/{forumcommentId}")
    public ForumCommentResource getForumCommentById(@PathVariable("forumcommentId") Long forumcommentId) {
        return forumCommentMapper.toResource(forumcommentService.getById(forumcommentId));
    }


    @PostMapping("/users/{userId}/forums/{forumId}/forumcomments")
    public ForumCommentResource createForumComment(@PathVariable Long userId, @PathVariable Long forumId, @RequestBody CreateForumCommentResource request) {
        ForumComment forumcomment = mapping.map(request, ForumComment.class);
        return mapping.map(forumcommentService.create(userId, forumId, forumcomment), ForumCommentResource.class);
    }

    @GetMapping("/forums/{forumId}/forumcomments")
    public Page<ForumCommentResource> getAllForumCommentsByForumId(@PathVariable Long forumId,Pageable pageable) {
        return forumCommentMapper.modelListToPage(forumcommentService.getForumCommentByForumId(forumId), pageable);
    }


    @DeleteMapping("/forumcomments/{forumcommentId}")
    public ResponseEntity<?> deleteForumComment(@PathVariable Long forumcommentId) {
        return forumcommentService.delete(forumcommentId);
    }
    @GetMapping("publicationcomments")
    public Page<PublicationCommentResource> getAllComments(Pageable pageable) {
        return publicationCommentMapper.modelListToPage(publicationCommentService.getAll(), pageable);
    }

    @GetMapping("publicationcomments/{commentId}")
    public PublicationCommentResource getCommentById(@PathVariable("commentId") Long commentId) {
        return publicationCommentMapper.toResource(publicationCommentService.getById(commentId));
    }

    @PostMapping("/users/{userId}/publications/{publicationId}/comments")
    public PublicationCommentResource createComment(@PathVariable Long userId, @PathVariable Long publicationId, @RequestBody CreatePublicationComment request) {
        PublicationComment comment = mapping.map(request, PublicationComment.class);
        return mapping.map(publicationCommentService.create(userId, publicationId, comment), PublicationCommentResource.class);
    }

    @GetMapping("/publications/{publicationId}/publicationcomments")
    public Page<PublicationCommentResource> getAllCommentsByPublicationId(@PathVariable Long publicationId,Pageable pageable) {
        return publicationCommentMapper.modelListToPage(publicationCommentService.getCommentByPublicationId(publicationId), pageable);
    }


    @DeleteMapping("/publicationcomments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        return publicationCommentService.delete(commentId);
    }




}
