package com.example.userservice.domain.service;

import com.example.userservice.domain.model.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
public interface ArtistService {

    List<Artist> getAll();
    Page<Artist> getAll(Pageable pageable);
    Artist getById(Long artistId);
    Artist update(Long artistId, Artist request);
    ResponseEntity<?> delete(Long artistId);
    Artist create(Artist artist);
}
