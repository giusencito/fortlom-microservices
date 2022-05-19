package com.example.supportservice.api;


import com.example.supportservice.domain.model.entity.Follow;
import com.example.supportservice.domain.service.FollowService;
import com.example.supportservice.mapping.FollowMapper;
import com.example.supportservice.resource.follow.CreateFollowResource;
import com.example.supportservice.resource.follow.FollowResource;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supportservice")
public class FollowController {
    @Autowired
    private FollowService followService;

    @Autowired
    private FollowMapper mapper;

    @Autowired
    private ModelMapper mapping;

    @GetMapping("/follows")
    public Page<FollowResource> getAllFollows(Pageable pageable) {
        return mapper.modelListToPage(followService.getAll(), pageable);
    }
    @GetMapping("/follows/{followId}")
    public FollowResource getFollowById(@PathVariable("followId") Long followId) {
        return mapper.toResource(followService.getById(followId));
    }
    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackCreateFollow")
    @PostMapping("/fanatics/{fanaticId}/artists/{artistId}/follows")
    public ResponseEntity<FollowResource> createFollow(@PathVariable Long fanaticId, @PathVariable Long artistId, @RequestBody CreateFollowResource request) {
        Follow comment = mapping.map(request, Follow.class);
        return ResponseEntity.ok(mapping.map(followService.create(fanaticId, artistId, comment), FollowResource.class));
    }
    public ResponseEntity<FollowResource> fallBackCreateFollow(@PathVariable Long fanaticId, @PathVariable Long artistId, @RequestBody CreateFollowResource request, RuntimeException e) {

        return new ResponseEntity("El fanatico " + fanaticId + "  no puede crear un seguimiento para el artista " +artistId +" por el momento", HttpStatus.OK);

    }
    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackGetAllFollowsByFanaticId")
    @GetMapping("/fanatics/{fanaticId}/follows")
    public ResponseEntity<Page<FollowResource>> getAllFollowsByFanaticId(@PathVariable Long fanaticId,Pageable pageable) {
        return ResponseEntity.ok(mapper.modelListToPage(followService.followsByFanaticId(fanaticId), pageable));
    }
    public ResponseEntity<Page<FollowResource>> fallBackGetAllFollowsByFanaticId(@PathVariable Long fanaticId,Pageable pageable, RuntimeException e) {
        return new ResponseEntity("El fanatico " + fanaticId + "  no puede mostrar sus seguimientos a artistas por el momento", HttpStatus.OK);
    }

    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackGetAllFollowsByArtistId")
    @GetMapping("/artists/{artistId}/follows")
    public ResponseEntity<Page<FollowResource>> getAllFollowsByArtistId(@PathVariable Long artistId,Pageable pageable) {
        return ResponseEntity.ok(mapper.modelListToPage(followService.followsByArtistId(artistId), pageable));
    }
    public ResponseEntity<Page<FollowResource>> fallBackGetAllFollowsByArtistId(@PathVariable Long artistId,Pageable pageable, RuntimeException e) {
        return new ResponseEntity("El artista " + artistId + "  no puede mostrar sus seguimientos de sus fanaticos por el momento", HttpStatus.OK);
    }

    @DeleteMapping("/follows/{followId}")
    public ResponseEntity<?> deleteFollow(@PathVariable Long followId) {
        return followService.delete(followId);
    }
}
