package com.example.publicationservice.api;


import com.example.publicationservice.domain.model.entity.Event;
import com.example.publicationservice.domain.model.entity.Publication;
import com.example.publicationservice.domain.service.EventService;
import com.example.publicationservice.domain.service.MultimediaService;
import com.example.publicationservice.domain.service.PublicationService;
import com.example.publicationservice.mapping.EventMapper;
import com.example.publicationservice.mapping.MultimediaMapper;
import com.example.publicationservice.mapping.PublicationMapper;
import com.example.publicationservice.models.event.CreateEventResource;
import com.example.publicationservice.models.event.EventResource;
import com.example.publicationservice.models.multimedia.MultimediaResource;
import com.example.publicationservice.models.publication.CreatePublicationResource;
import com.example.publicationservice.models.publication.PublicationResource;
import com.example.publicationservice.models.publication.UpdatePublicationResource;
import com.example.publicationservice.util.ImageModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private MultimediaService multimediaService;

    @Autowired
    private MultimediaMapper multimediaMapper;

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private PublicationMapper publicationMapper;

    @Autowired
    private ModelMapper mapping;

    @GetMapping("events")
    public Page<EventResource> getAllEvents(Pageable pageable) {
        return eventMapper.modelListToPage(eventService.getAllEvents(), pageable);
    }
    @GetMapping("/event/{eventId}")
    public EventResource getEventById(@PathVariable Long eventId) {
        return eventMapper.toResource(eventService.getEventById(eventId));
    }
    @GetMapping("/artist/{artistId}/events")
    public Page<EventResource> getAllEventsByArtistId(@PathVariable Long artistId,Pageable pageable) {
        return eventMapper.modelListToPage(eventService.getEventsByArtistId(artistId), pageable);
    }
    @PostMapping("/artist/{artistId}/events")
    public EventResource createEvent(@PathVariable Long artistId,@RequestBody CreateEventResource request) {
        Event event = mapping.map(request, Event.class);
        return mapping.map(eventService.createEvent(artistId, event), EventResource.class);
    }
    @DeleteMapping("/event/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) {
        return eventService.deleteEvent(eventId);
    }
    @PutMapping("/eventupdatelikeup/{eventId}")
    public EventResource updateEventlikeup(@PathVariable Long eventId) {
        return eventMapper.toResource(eventService.updateEventlikesup(eventId));
    }
    @PutMapping("/eventupdatelikedown/{eventId}")
    public EventResource updateEventlikedown(@PathVariable Long eventId) {
        return eventMapper.toResource(eventService.updateEventlikesdown(eventId));
    }
    @PutMapping("/eventupdatereleseadedate/{eventId}/releasedate/{releasedate}")
    public EventResource updateEventreleaseddate(@PathVariable Long eventId,@PathVariable String releasedate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date dataFormateada = format.parse(releasedate);
        return eventMapper.toResource(eventService.updateEventreleasedate(eventId,dataFormateada));
    }



    @GetMapping("multimedias")
    public Page<MultimediaResource> getAllMultimedias(Pageable pageable) {
        return multimediaMapper.modelListToPage(multimediaService.getAll(), pageable);
    }


    @GetMapping("/multimedias/image/info/{multimediaId}")
    public ImageModel getImageDetails (@PathVariable("multimediaId") Long multimediaId) throws IOException {
        return  multimediaService.getImageDetails(multimediaId);
    }



    @PostMapping("/publications/{publicationId}/multimedias")
    public void createComment( @PathVariable Long publicationId,@RequestParam("file") MultipartFile file) throws IOException {
        multimediaService.create(publicationId,file);
    }

    @GetMapping("/publications/{publicationId}/multimedias")
    public List<ImageModel> getAllmultimediasByPublicationId(@PathVariable Long publicationId, Pageable pageable) {
        return multimediaService.getMultimediaByPublicationId(publicationId);
    }





    @DeleteMapping("/multimedias/{multimediaId}")
    public ResponseEntity<?> deleteMultimedia(@PathVariable Long multimediaId) {
        return multimediaService.delete(multimediaId);
    }



    @GetMapping("publications")
    public Page<PublicationResource> getAllPublications(Pageable pageable) {
        return publicationMapper.modelListToPage(publicationService.getAll(), pageable);
    }

    @GetMapping("/publications/{publicationId}")
    public PublicationResource getPublicationById(@PathVariable("publicationId") Long publicationId) {
        return publicationMapper.toResource(publicationService.getById(publicationId));
    }

    @PostMapping("/artists/{artistId}/publications")
    public PublicationResource createPublication(@PathVariable Long artistId,@RequestBody CreatePublicationResource request) {
        Publication publication = mapping.map(request, Publication.class);
        return mapping.map(publicationService.create(artistId, publication), PublicationResource.class);
    }

    @PutMapping("/publications/{publicationId}")
    public PublicationResource updatePublication(@PathVariable Long publicationId, @RequestBody UpdatePublicationResource request) {
        return publicationMapper.toResource(publicationService.update(publicationId, publicationMapper.toModel(request)));
    }

    @DeleteMapping("/publications/{publicationId}")
    public ResponseEntity<?> deletePublication(@PathVariable Long publicationId) {
        return publicationService.delete(publicationId);
    }

    @GetMapping("/artists/{artistId}/publications")
    public Page<PublicationResource> getAllPublicationByArtistId(@PathVariable Long artistId,Pageable pageable) {
        return publicationMapper.modelListToPage(publicationService.getPublicationByArtistId(artistId), pageable);
    }






}
