package com.example.supportservice.api;


import com.example.supportservice.domain.model.entity.Follow;
import com.example.supportservice.domain.model.entity.Rate;
import com.example.supportservice.domain.service.FollowService;
import com.example.supportservice.domain.service.RateService;
import com.example.supportservice.mapping.FollowMapper;
import com.example.supportservice.mapping.RateMapper;
import com.example.supportservice.resource.follow.CreateFollowResource;
import com.example.supportservice.resource.follow.FollowResource;
import com.example.supportservice.resource.rate.CreateRateResource;
import com.example.supportservice.resource.rate.RateResource;
import com.example.supportservice.resource.rate.UpdateRateResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/support")
public class SupportController {

    @Autowired
    private FollowService followService;

    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private RateService rateService;

    @Autowired
    private RateMapper rateMapper;
    @Autowired
    private ModelMapper mapping;



    @GetMapping("follows")
    public Page<FollowResource> getAllFollows(Pageable pageable) {
        return followMapper.modelListToPage(followService.getAll(), pageable);
    }
    @GetMapping("rates")
    public Page<RateResource> getAllRates(Pageable pageable) {
        return rateMapper.modelListToPage(rateService.getAll(), pageable);
    }
    @GetMapping("/follows/{followId}")
    public FollowResource getFollowById(@PathVariable("followId") Long followId) {
        return followMapper.toResource(followService.getById(followId));
    }
    @GetMapping("/rates/{rateId}")
    public RateResource getRateById(@PathVariable("rateId") Long followId) {
        return rateMapper.toResource(rateService.getById(followId));
    }
    @PostMapping("/fanatics/{fanaticId}/artists/{artistId}/follows")
    public FollowResource createFollow(@PathVariable Long fanaticId, @PathVariable Long artistId, @RequestBody CreateFollowResource request) {
        Follow comment = mapping.map(request, Follow.class);
        return mapping.map(followService.create(fanaticId, artistId, comment), FollowResource.class);
    }
    @GetMapping("/fanatics/{fanaticId}/follows")
    public Page<FollowResource> getAllFollowsByFanaticId(@PathVariable Long fanaticId,Pageable pageable) {
        return followMapper.modelListToPage(followService.followsByFanaticId(fanaticId), pageable);
    }
    @GetMapping("/artists/{artistId}/follows")
    public Page<FollowResource> getAllFollowsByArtistId(@PathVariable Long artistId,Pageable pageable) {
        return followMapper.modelListToPage(followService.followsByArtistId(artistId), pageable);
    }
    @PostMapping("/fanatics/{fanaticId}/artists/{artistId}/rates")
    public RateResource createRate(@PathVariable Long fanaticId, @PathVariable Long artistId, @RequestBody CreateRateResource request) {
        Rate comment = mapping.map(request, Rate.class);
        return mapping.map(rateService.create(fanaticId, artistId, comment), RateResource.class);
    }
    @GetMapping("/fanatics/{fanaticId}/rates")
    public Page<RateResource> getAllRatesByFanaticId(@PathVariable Long fanaticId,Pageable pageable) {
        return rateMapper.modelListToPage(rateService.ratesByFanaticId(fanaticId), pageable);
    }
    @GetMapping("/artists/{artistId}/rates")
    public Page<RateResource> getAllRateByArtistId(@PathVariable Long artistId,Pageable pageable) {
        return rateMapper.modelListToPage(rateService.ratesByArtistId(artistId), pageable);
    }
    @DeleteMapping("/follows/{followId}")
    public ResponseEntity<?> deleteFollow(@PathVariable Long followId) {
        return followService.delete(followId);
    }

    @DeleteMapping("/rates/{rateId}")
    public ResponseEntity<?> deleteRate(@PathVariable Long rateId) {
        return rateService.delete(rateId);
    }


    @PutMapping("/rates/{rateId}")
    public RateResource updateRate(@PathVariable Long rateId, @RequestBody UpdateRateResource request) {
        return rateMapper.toResource(rateService.update(rateId, rateMapper.toModel(request)));
    }

}
