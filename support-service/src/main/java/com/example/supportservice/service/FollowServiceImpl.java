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
        boolean check1= restTemplate.getForObject("http://localhost:8001/api/v1/artists/check/"+ArtistId,boolean.class);
        boolean check2= restTemplate.getForObject("http://localhost:8001/api/v1/fanatics/check/"+fanaticId,boolean.class);
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
        return followRepository.findByFanaticid(FanaticId);
    }

    @Override
    public List<Follow> followsByArtistId(Long ArtistId) {
        return followRepository.findByArtistid(ArtistId);
    }

    @Override
    public ResponseEntity<?> delete(Long followId) {
        return followRepository.findById(followId).map(post -> {
            followRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, followId));
    }
}
