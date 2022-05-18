package com.example.supportservice.service;


import com.example.supportservice.domain.model.entity.Follow;
import com.example.supportservice.domain.persistence.FollowRepository;
import com.example.supportservice.domain.service.FollowService;
import com.example.supportservice.shared.execption.ResourceNotFoundException;
import com.example.supportservice.shared.execption.ResourcePerzonalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {



    private static final String ENTITY = "Follow";

    @Autowired
    private FollowRepository followRepository;


    @Autowired
    private RestTemplate restTemplate;

    public FollowServiceImpl() {

    }


    @Override
    public List<Follow> getAll() {
        return followRepository.findAll();
    }

    @Override
    public Page<Follow> getAll(Pageable pageable) {
        return followRepository.findAll(pageable);
    }

    @Override
    public Follow getById(Long followId) {
        return followRepository.findById(followId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, followId));
    }

    @Override
    public Follow create(Long fanaticId, Long ArtistId, Follow request) {
        boolean check1= restTemplate.getForObject("http://user-service/api/v1/userservice/artists/check/"+ArtistId,boolean.class);
        boolean check2= restTemplate.getForObject("http://user-service/api/v1/userservice/fanatics/check/"+fanaticId,boolean.class);
        if(check1 && check2){

            request.setArtistid(ArtistId);
            request.setFanaticid(fanaticId);
            return followRepository.save(request);

        }else {
            throw  new ResourcePerzonalized("id inexistente");
        }


    }

    @Override
    public List<Follow> followsByFanaticId(Long FanaticId) {
        boolean check1= restTemplate.getForObject("http://user-service/api/v1/userservice/fanatics/check/"+FanaticId,boolean.class);
        if(check1)
        return followRepository.findByFanaticid(FanaticId);
        else throw new ResourcePerzonalized("id inexistente de Fanatico");
    }

    @Override
    public List<Follow> followsByArtistId(Long ArtistId) {
        boolean check1= restTemplate.getForObject("http://user-service/api/v1/userservice/artists/check/"+ArtistId,boolean.class);
        if(check1)
        return followRepository.findByArtistid(ArtistId);
        else throw new ResourcePerzonalized("id inexistente de Artista");
    }

    @Override
    public ResponseEntity<?> delete(Long followId) {
        return followRepository.findById(followId).map(post -> {
            followRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, followId));
    }
}
