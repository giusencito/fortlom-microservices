package com.example.publicationservice.api;

import com.example.publicationservice.domain.model.entity.Event;
import com.example.publicationservice.domain.service.EventService;
import com.example.publicationservice.mapping.EventMapper;
import com.example.publicationservice.models.event.CreateEventResource;
import com.example.publicationservice.models.event.EventResource;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/contentservice")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper mapper;

    @Autowired
    private ModelMapper mapping;

    @GetMapping("/events")
    public Page<EventResource> getAllEvents(Pageable pageable) {
        return mapper.modelListToPage(eventService.getAllEvents(), pageable);
    }
    @GetMapping("/event/{eventId}")
    public EventResource getEventById(@PathVariable Long eventId) {
        return mapper.toResource(eventService.getEventById(eventId));
    }
    @GetMapping("/artist/{artistId}/events")
    public Page<EventResource> getAllEventsByArtistId(@PathVariable Long artistId,Pageable pageable) {
        return mapper.modelListToPage(eventService.getEventsByArtistId(artistId), pageable);
    }
    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackCreateEvent")
    @PostMapping("/artist/{artistId}/events")
    public ResponseEntity<EventResource> createEvent(@PathVariable Long artistId,@RequestBody CreateEventResource request) {
        Event event = mapping.map(request, Event.class);
        return ResponseEntity.ok(mapping.map(eventService.createEvent(artistId, event), EventResource.class));
    }
    public ResponseEntity<EventResource> fallBackCreateEvent(@PathVariable Long artistId,@RequestBody CreateEventResource request, RuntimeException e) {
        return new ResponseEntity("El artista " + artistId + "  no puede crear un evento por el momento", HttpStatus.OK);


    }
    @DeleteMapping("/event/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) {
        return eventService.deleteEvent(eventId);
    }
    @PutMapping("/eventupdatelikeup/{eventId}")
    public EventResource updateEventlikeup(@PathVariable Long eventId) {
        return mapper.toResource(eventService.updateEventlikesup(eventId));
    }
    @PutMapping("/eventupdatelikedown/{eventId}")
    public EventResource updateEventlikedown(@PathVariable Long eventId) {
        return mapper.toResource(eventService.updateEventlikesdown(eventId));
    }
    @PutMapping("/eventupdatereleseadedate/{eventId}/releasedate/{releasedate}")
    public EventResource updateEventreleaseddate(@PathVariable Long eventId,@PathVariable String releasedate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date dataFormateada = format.parse(releasedate);
        return mapper.toResource(eventService.updateEventreleasedate(eventId,dataFormateada));
    }

}
