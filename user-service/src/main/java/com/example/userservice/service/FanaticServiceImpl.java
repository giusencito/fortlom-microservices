package com.example.userservice.service;

import com.example.userservice.domain.model.entity.Fanatic;
import com.example.userservice.domain.persistence.FanaticRepository;
import com.example.userservice.domain.service.FanaticService;
import com.example.userservice.shared.execption.ResourceNotFoundException;
import com.example.userservice.shared.execption.ResourcePerzonalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
public class FanaticServiceImpl implements FanaticService {
    private static final String ENTITY = "Artist";

    @Autowired
    private FanaticRepository fanaticRepository;

    @Override
    public List<Fanatic> getAll() {
        return fanaticRepository.findAll();
    }

    @Override
    public Page<Fanatic> getAll(Pageable pageable) {
        return fanaticRepository.findAll(pageable);
    }

    @Override
    public Fanatic getById(Long fanaticId) {
        return fanaticRepository.findById(fanaticId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, fanaticId));
    }

    @Override
    public Fanatic update(Long artistId, Fanatic request) {
        return fanaticRepository.findById(artistId).map(dueño -> {
            dueño.setFanaticalias(request.getFanaticalias());


            fanaticRepository.save(dueño);
            return dueño;
        }

        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public ResponseEntity<?> delete(Long artistId) {
        return fanaticRepository.findById(artistId).map(post -> {
            fanaticRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }
    @Override
    public Fanatic create(Fanatic artist) {
        if(fanaticRepository.existsByUsername(artist.getUsername()))
            throw  new ResourcePerzonalized("ya exsite este nombre de usuario");
        if (fanaticRepository.existsByEmail(artist.getEmail()))
            throw  new ResourcePerzonalized("ya exsite este correo electronico");

        return fanaticRepository.save(artist);
    }

    @Override
    public boolean existsfanatic(Long fanaticid) {
        return fanaticRepository.existsById(fanaticid);
    }
}
