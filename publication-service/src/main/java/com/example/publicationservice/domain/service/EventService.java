package com.example.publicationservice.domain.service;

import com.example.publicationservice.domain.model.entity.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
public interface EventService {
    List<Event> getAllEvents();
    Page<Event> getAllEvents(Pageable pageable);
    Event getEventById(Long eventId);
    Event createEvent(Long Artist,Event event);
    Event updateEventlikesup(Long eventId);
    Event updateEventlikesdown(Long eventId);
    Event updateEventreleasedate(Long eventId, Date releasedDate);
    List<Event> getEventsByArtistId(Long artistId);
    ResponseEntity<?> deleteEvent(Long eventId);
}
