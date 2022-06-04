package com.example.userservice.api;
import com.example.userservice.domain.service.ArtistService;
import com.example.userservice.mapping.ArtistMapper;
import com.example.userservice.models.Artist.ArtistResource;
import com.example.userservice.models.Artist.CreateArtistResource;
import com.example.userservice.models.Artist.UpdateArtistResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userservice/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistMapper mapper;



    @GetMapping
    public Page<ArtistResource> getAllFanatics(Pageable pageable) {
        return mapper.modelListToPage(artistService.getAll(), pageable);
    }
    @GetMapping("/check/{artistId}")
    public boolean existsartistid(@PathVariable("artistId") Long artistId){
        return artistService.existsartist(artistId);
    }
    @GetMapping("{artistId}")
    public ArtistResource getUserById(@PathVariable("artistId") Long artistId) {
        return mapper.toResource(artistService.getById(artistId));
    }

    @PostMapping
    public ArtistResource createUser(@RequestBody CreateArtistResource request) {

        return mapper.toResource(artistService.create(mapper.toModel(request)));
    }
    @PutMapping("{artistId}")
    public ArtistResource updateUser(@PathVariable Long artistId, @RequestBody UpdateArtistResource request) {
        return mapper.toResource(artistService.update(artistId, mapper.toModel(request)));
    }
    @DeleteMapping("{artistId}")
    public ResponseEntity<?> deletePost(@PathVariable Long artistId) {
        return artistService.delete(artistId);
    }



    @PutMapping("/artist/{artistId}/InstagramAccount")
    public ArtistResource updateInstagramAccount(@PathVariable Long artistId, @RequestBody UpdateArtistResource request){
        return mapper.toResource(artistService.setInstagramAccount(artistId,mapper.toModel(request)));

    }
    @PutMapping("/artist/{artistId}/TwitterAccount")
    public ArtistResource updateTwitterAccount(@PathVariable Long artistId, @RequestBody UpdateArtistResource request){
        return mapper.toResource(artistService.setTwitterAccount(artistId,mapper.toModel(request)));
    }
    @PutMapping("/artist/{artistId}/FacebookAccount")
    public ArtistResource updateFacebookAccount(@PathVariable Long artistId, @RequestBody UpdateArtistResource request){
        return mapper.toResource(artistService.setFacebookAccount(artistId,mapper.toModel(request)));
    }



}
