package com.example.publicationservice.service;

import com.example.publicationservice.domain.model.entity.Event;
import com.example.publicationservice.domain.persistence.EventRepository;
import com.example.publicationservice.domain.service.EventService;
import com.example.publicationservice.shared.execption.ResourceNotFoundException;
import com.example.publicationservice.shared.execption.ResourcePerzonalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private static final String ENTITY = "Event";

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, eventId));
    }

    @Override
    public Event createEvent(Long Artist, Event request) {

        boolean check= restTemplate.getForObject("http://user-service/api/v1/userservice/artists/check/"+Artist,boolean.class);
        if(check){
            request.setArtistid(Artist);
            Date date = new Date();
            request.setRegisterdate(date);
            request.setLikes((long)0);
            return eventRepository.save(request);
        }else{
            throw  new ResourcePerzonalized("id inexistente");
        }



    }

    @Override
    public Event updateEventlikesup(Long eventId) {
        return eventRepository.findById(eventId).map(post->{
            post.setLikes(post.getLikes()+1);
            eventRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, eventId));
    }

    @Override
    public Event updateEventlikesdown(Long eventId) {
        return eventRepository.findById(eventId).map(post->{
            if(post.getLikes()!=0){
                post.setLikes(post.getLikes()-1);
            }
            eventRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, eventId));
    }

    @Override
    public Event updateEventreleasedate(Long eventId, Date releasedDate) {
        return eventRepository.findById(eventId).map(post->{
            post.setReleasedDate(releasedDate);
            eventRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, eventId));
    }

    @Override
    public List<Event> getEventsByArtistId(Long artistId) {

        boolean check= restTemplate.getForObject("http://user-service/api/v1/userservice/artists/check/"+artistId,boolean.class);
        if(check){

        return eventRepository.findByArtistid(artistId);}
        else {
            throw  new ResourcePerzonalized("id inexistente");

        }
    }

    @Override
    public ResponseEntity<?> deleteEvent(Long eventId) {
        return eventRepository.findById(eventId).map(event -> {
            eventRepository.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, eventId));
    }
}
