package com.example.publicationservice.api;

import com.example.publicationservice.domain.model.entity.Publication;
import com.example.publicationservice.domain.service.PublicationService;
import com.example.publicationservice.mapping.PublicationMapper;
import com.example.publicationservice.models.publication.CreatePublicationResource;
import com.example.publicationservice.models.publication.PublicationResource;
import com.example.publicationservice.models.publication.UpdatePublicationResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private PublicationMapper mapper;

    @Autowired
    private ModelMapper mapping;

    @GetMapping("/publications")
    public Page<PublicationResource> getAllPublications(Pageable pageable) {
        return mapper.modelListToPage(publicationService.getAll(), pageable);
    }

    @GetMapping("/publications/{publicationId}")
    public PublicationResource getPublicationById(@PathVariable("publicationId") Long publicationId) {
        return mapper.toResource(publicationService.getById(publicationId));
    }

    @PostMapping("/artists/{artistId}/publications")
    public PublicationResource createPublication(@PathVariable Long artistId,@RequestBody CreatePublicationResource request) {
        Publication publication = mapping.map(request, Publication.class);
        return mapping.map(publicationService.create(artistId, publication), PublicationResource.class);
    }

    @PutMapping("/publications/{publicationId}")
    public PublicationResource updatePublication(@PathVariable Long publicationId, @RequestBody UpdatePublicationResource request) {
        return mapper.toResource(publicationService.update(publicationId, mapper.toModel(request)));
    }

    @DeleteMapping("/publications/{publicationId}")
    public ResponseEntity<?> deletePublication(@PathVariable Long publicationId) {
        return publicationService.delete(publicationId);
    }

    @GetMapping("/artists/{artistId}/publications")
    public Page<PublicationResource> getAllPublicationByArtistId(@PathVariable Long artistId,Pageable pageable) {
        return mapper.modelListToPage(publicationService.getPublicationByArtistId(artistId), pageable);
    }
}
