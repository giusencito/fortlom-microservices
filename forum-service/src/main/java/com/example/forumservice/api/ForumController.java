package com.example.forumservice.api;
import com.example.forumservice.domain.model.entity.Forum;
import com.example.forumservice.domain.service.ForumService;
import com.example.forumservice.mapping.ForumMapper;
import com.example.forumservice.models.CreateForumResource;
import com.example.forumservice.models.ForumResource;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1")
public class ForumController {
    @Autowired
    private ForumService forumService;

    @Autowired
    private ForumMapper mapper;

    @Autowired
    private ModelMapper mapping;

    @GetMapping("/forums")
    public Page<ForumResource> getAllForums(Pageable pageable) {
        return mapper.modelListToPage(forumService.getAllForums(), pageable);
    }
    @GetMapping("/forums/{forumId}")
    public ResponseEntity<ForumResource> getForumById(@PathVariable Long forumId) {

        return ResponseEntity.ok(mapper.toResource(forumService.getForumById(forumId)));

    }
    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackGetAllForumsByUsersId")
    @GetMapping("/user/{usersId}/forums")
    public ResponseEntity<Page<ForumResource>> getAllForumsByusersId(@PathVariable Long usersId,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(forumService.getForumsByUserId(usersId), pageable));
    }
    public ResponseEntity<Page<ForumResource>> fallBackGetAllForumsByUsersId(@PathVariable Long usersId,Pageable pageable, RuntimeException e) {

        return new ResponseEntity("El usuario " + usersId + "  no puede retornar sus foros por el momento", HttpStatus.OK);

    }
    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackCreateForum")
    @PostMapping("/user/{usersId}/forums")
    public ResponseEntity<ForumResource> createForum(@PathVariable Long usersId,@RequestBody CreateForumResource request) {
        Forum forum = mapping.map(request, Forum.class);
        return ResponseEntity.ok(mapping.map(forumService.createForum(usersId, forum), ForumResource.class));
    }
    public ResponseEntity<ForumResource> fallBackCreateForum(@PathVariable Long usersId,@RequestBody CreateForumResource request, RuntimeException e) {

        return new ResponseEntity("El usuario " + usersId + "  no puede crear un foro", HttpStatus.OK);

    }
    @DeleteMapping("/forums/{forumId}")
    public ResponseEntity<?> deleteForum(@PathVariable Long forumId) {
        return forumService.deleteForum(forumId);
    }

    @GetMapping("/check/{forumId}")
    public boolean existforumid(@PathVariable("forumId") Long forumId){
        return forumService.existsforum(forumId);
    }

}
