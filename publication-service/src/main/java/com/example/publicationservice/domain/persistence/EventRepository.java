package com.example.publicationservice.domain.persistence;

import com.example.publicationservice.domain.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    List<Event>findByArtistid(Long artistId);

}
