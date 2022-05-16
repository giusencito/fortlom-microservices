package com.example.userservice.service;

import com.example.userservice.domain.model.entity.Artist;
import com.example.userservice.domain.persistence.ArtistRepository;
import com.example.userservice.domain.service.ArtistService;
import com.example.userservice.shared.execption.ResourceNotFoundException;
import com.example.userservice.shared.execption.ResourcePerzonalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArtistServiceImpl implements ArtistService {

    private static final String ENTITY = "Artist";

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public List<Artist> getAll() {
        return artistRepository.findAll();
    }

    @Override
    public Page<Artist> getAll(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }

    @Override
    public Artist getById(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist update(Long artistId, Artist request) {
        return artistRepository.findById(artistId).map(post->{

            post.setArtistfollowers(request.getArtistfollowers());
            artistRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public ResponseEntity<?> delete(Long artistId) {
        return artistRepository.findById(artistId).map(post -> {
            artistRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }
    @Override
    public Artist create(Artist artist) {
        if(artistRepository.existsByUsername(artist.getUsername()))
            throw  new ResourcePerzonalized("ya exsite este nombre de usuario");
        if (artistRepository.existsByEmail(artist.getEmail()))
            throw  new ResourcePerzonalized("ya exsite este correo electronico");

        return artistRepository.save(artist);
    }

    @Override
    public boolean existsartist(Long artistId) {
        return artistRepository.existsById(artistId);
    }
}
