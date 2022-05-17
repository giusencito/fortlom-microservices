package com.example.userservice.api;


import com.example.userservice.domain.service.ArtistService;
import com.example.userservice.domain.service.FanaticService;
import com.example.userservice.mapping.ArtistMapper;
import com.example.userservice.mapping.FanaticMapper;
import com.example.userservice.models.Artist.ArtistResource;
import com.example.userservice.models.Artist.CreateArtistResource;
import com.example.userservice.models.Artist.UpdateArtistResource;
import com.example.userservice.models.Fanatic.CreateFanaticResource;
import com.example.userservice.models.Fanatic.FanaticResource;
import com.example.userservice.models.Fanatic.UpdateFanaticResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistMapper artistMapper;

    @Autowired
    private FanaticService fanaticService;

    @Autowired
    private FanaticMapper fanaticMapper;



    @GetMapping("artists")
    public Page<ArtistResource> getAllArtits(Pageable pageable) {
        return artistMapper.modelListToPage(artistService.getAll(), pageable);
    }
    @GetMapping("fanatics")
    public Page<FanaticResource> getAllFanatics(Pageable pageable) {
        return fanaticMapper.modelListToPage(fanaticService.getAll(), pageable);
    }
    @GetMapping("/checkartist/{artistId}")
    public boolean existsartistid(@PathVariable("artistId") Long artistId){
        return artistService.existsartist(artistId);
    }
    @GetMapping("/checkfanatics/{fanaticId}")
    public boolean existfanaticid(@PathVariable("fanaticId") Long fanaticId){
        return fanaticService.existsfanatic(fanaticId);
    }
    @GetMapping("artists/{artistId}")
    public ArtistResource getartistIdById(@PathVariable("artistId") Long artistId) {
        return artistMapper.toResource(artistService.getById(artistId));
    }
    @GetMapping("fanatics/{fanaticId}")
    public FanaticResource getUserById(@PathVariable("fanaticId") Long fanaticId) {
        return fanaticMapper.toResource(fanaticService.getById(fanaticId));
    }
    @PostMapping("artists")
    public ArtistResource createArtist(@RequestBody CreateArtistResource request) {

        return artistMapper.toResource(artistService.create(artistMapper.toModel(request)));
    }
    @PostMapping("fanatics")
    public FanaticResource createUser(@RequestBody CreateFanaticResource request) {

        return fanaticMapper.toResource(fanaticService.create(fanaticMapper.toModel(request)));
    }
    @PutMapping("artists/{artistId}")
    public ArtistResource updateUser(@PathVariable Long artistId, @RequestBody UpdateArtistResource request) {
        return artistMapper.toResource(artistService.update(artistId, artistMapper.toModel(request)));
    }
    @PutMapping("fanatics/{fanaticId}")
    public FanaticResource updateUser(@PathVariable Long fanaticId, @RequestBody UpdateFanaticResource request) {
        return fanaticMapper.toResource(fanaticService.update(fanaticId, fanaticMapper.toModel(request)));
    }
    @DeleteMapping("artists/{artistId}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long artistId) {
        return artistService.delete(artistId);
    }

    @DeleteMapping("fanatics/{fanaticId}")
    public ResponseEntity<?> deletePost(@PathVariable Long fanaticId) {
        return fanaticService.delete(fanaticId);
    }
}
