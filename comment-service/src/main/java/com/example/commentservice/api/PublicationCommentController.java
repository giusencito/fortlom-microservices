package com.example.commentservice.api;

import com.example.commentservice.domain.model.entity.PublicationComment;
import com.example.commentservice.domain.service.PublicationCommentService;
import com.example.commentservice.mapping.PublicationCommentMapper;
import com.example.commentservice.models.PublicationComment.CreatePublicationComment;
import com.example.commentservice.models.PublicationComment.PublicationCommentResource;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/commentservice")
public class PublicationCommentController {
    @Autowired
    private PublicationCommentService commentService;

    @Autowired
    private PublicationCommentMapper mapper;

    @Autowired
    private ModelMapper mapping;

    @GetMapping("/comments")
    public Page<PublicationCommentResource> getAllComments(Pageable pageable) {
        return mapper.modelListToPage(commentService.getAll(), pageable);
    }

    @GetMapping("/comments/{commentId}")
    public PublicationCommentResource getCommentById(@PathVariable("commentId") Long commentId) {
        return mapper.toResource(commentService.getById(commentId));
    }

    @CircuitBreaker(name = "userandpublicCB", fallbackMethod = "fallBackCreatePublicationComment")
    @PostMapping("/users/{userId}/publications/{publicationId}/comments")
    public ResponseEntity<PublicationCommentResource> CreatePublicationComment(@PathVariable Long userId, @PathVariable Long publicationId, @RequestBody CreatePublicationComment request) {
        PublicationComment comment = mapping.map(request, PublicationComment.class);

        return ResponseEntity.ok(mapping.map(commentService.create(userId, publicationId, comment), PublicationCommentResource.class));
    }
    public ResponseEntity<PublicationCommentResource> fallBackCreatePublicationComment(@PathVariable Long userId, @PathVariable Long publicationId, @RequestBody CreatePublicationComment request, RuntimeException e) {
        return new ResponseEntity("El usuario" + userId+"para el"+"La publicacion " + publicationId + "  no puede crear comentarios", HttpStatus.OK);


    }
    @CircuitBreaker(name = "PublicationCB", fallbackMethod = "fallBackGetAllCommentsByPublicationId")
    @GetMapping("/publications/{publicationId}/comments")
    public ResponseEntity<Page<PublicationCommentResource>> getAllCommentsByPublicationId(@PathVariable Long publicationId,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(commentService.getCommentByPublicationId(publicationId), pageable));
    }
    public ResponseEntity<Page<PublicationCommentResource>> fallBackGetAllCommentsByPublicationId(@PathVariable Long publicationId,Pageable pageable, RuntimeException e) {
        return new ResponseEntity("El publicacion " + publicationId + "  no puede crear comentarios", HttpStatus.OK);

    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        return commentService.delete(commentId);
    }
}
