package com.example.supportservice.api;


import com.example.supportservice.domain.model.entity.Follow;
import com.example.supportservice.domain.service.FollowService;
import com.example.supportservice.mapping.FollowMapper;
import com.example.supportservice.resource.follow.CreateFollowResource;
import com.example.supportservice.resource.follow.FollowResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
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
    @PostMapping("/fanatics/{fanaticId}/artists/{artistId}/follows")
    public FollowResource createFollow(@PathVariable Long fanaticId, @PathVariable Long artistId, @RequestBody CreateFollowResource request) {
        Follow comment = mapping.map(request, Follow.class);
        return mapping.map(followService.create(fanaticId, artistId, comment), FollowResource.class);
    }
    @GetMapping("/fanatics/{fanaticId}/follows")
    public Page<FollowResource> getAllFollowsByFanaticId(@PathVariable Long fanaticId,Pageable pageable) {
        return mapper.modelListToPage(followService.followsByFanaticId(fanaticId), pageable);
    }
    @GetMapping("/artists/{artistId}/follows")
    public Page<FollowResource> getAllFollowsByArtistId(@PathVariable Long artistId,Pageable pageable) {
        return mapper.modelListToPage(followService.followsByArtistId(artistId), pageable);
    }
    @DeleteMapping("/follows/{followId}")
    public ResponseEntity<?> deleteFollow(@PathVariable Long followId) {
        return followService.delete(followId);
    }
}
