package com.example.supportservice.api;



import com.example.supportservice.domain.model.entity.Rate;
import com.example.supportservice.domain.service.RateService;
import com.example.supportservice.mapping.RateMapper;
import com.example.supportservice.resource.rate.CreateRateResource;
import com.example.supportservice.resource.rate.RateResource;
import com.example.supportservice.resource.rate.UpdateRateResource;
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
public class RateController {

    @Autowired
    private RateService rateService;

    @Autowired
    private RateMapper mapper;

    @Autowired
    private ModelMapper mapping;
    @GetMapping("/rates")
    public Page<RateResource> getAllRates(Pageable pageable) {
        return mapper.modelListToPage(rateService.getAll(), pageable);
    }
    @GetMapping("/rates/{rateId}")
    public RateResource getRateById(@PathVariable("rateId") Long followId) {
        return mapper.toResource(rateService.getById(followId));
    }
    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackCreateRate")
    @PostMapping("/fanatics/{fanaticId}/artists/{artistId}/rates")
    public ResponseEntity<RateResource> createRate(@PathVariable Long fanaticId, @PathVariable Long artistId, @RequestBody CreateRateResource request) {
        Rate comment = mapping.map(request, Rate.class);
        return ResponseEntity.ok(mapping.map(rateService.create(fanaticId, artistId, comment), RateResource.class));
    }
    public ResponseEntity<RateResource> fallBackCreateRate(@PathVariable Long fanaticId, @PathVariable Long artistId, @RequestBody CreateRateResource request, RuntimeException e) {

        return new ResponseEntity("El fanatico " + fanaticId + "  no puede crear una calificacion para el artista " +artistId +" por el momento", HttpStatus.OK);
    }

    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackGetAllRatesByFanaticId")
    @GetMapping("/fanatics/{fanaticId}/rates")
    public ResponseEntity<Page<RateResource>> getAllRatesByFanaticId(@PathVariable Long fanaticId,Pageable pageable) {
        return ResponseEntity.ok(mapper.modelListToPage(rateService.ratesByFanaticId(fanaticId), pageable));
    }
    public ResponseEntity<Page<RateResource>> fallBackGetAllRatesByFanaticId(@PathVariable Long fanaticId,Pageable pageable,  RuntimeException e) {
        return new ResponseEntity("El fanatico " + fanaticId + "  no puede mostrar sus calificaciones a artistas por el momento", HttpStatus.OK);
    }

    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackGetAllRatesByArtistId")
    @GetMapping("/artists/{artistId}/rates")
    public ResponseEntity<Page<RateResource>> getAllRateByArtistId(@PathVariable Long artistId,Pageable pageable) {
        return ResponseEntity.ok(mapper.modelListToPage(rateService.ratesByArtistId(artistId), pageable));
    }
    public ResponseEntity<Page<RateResource>> fallBackGetAllRatesByArtistId(@PathVariable Long artistId,Pageable pageable, RuntimeException e) {
        return new ResponseEntity("El artista " + artistId + "  no puede mostrar sus calificaciones de sus fanaticos por el momento", HttpStatus.OK);
    }

    @DeleteMapping("/rates/{rateId}")
    public ResponseEntity<?> deleteFollow(@PathVariable Long rateId) {
        return rateService.delete(rateId);
    }


    @PutMapping("/rates/{rateId}")
    public RateResource updateRate(@PathVariable Long rateId, @RequestBody UpdateRateResource request) {
        return mapper.toResource(rateService.update(rateId, mapper.toModel(request)));
    }
}
