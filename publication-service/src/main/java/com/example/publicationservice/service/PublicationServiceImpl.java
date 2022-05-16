package com.example.publicationservice.service;

import com.example.publicationservice.domain.model.entity.Publication;
import com.example.publicationservice.domain.persistence.PublicationRepository;
import com.example.publicationservice.domain.service.PublicationService;
import com.example.publicationservice.shared.execption.Message;
import com.example.publicationservice.shared.execption.ResourceNotFoundException;
import com.example.publicationservice.shared.execption.ResourcePerzonalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;


@Service
public class PublicationServiceImpl  implements PublicationService {
    private static final String ENTITY = "Publication";
    private static final String ENTITY2 = "Artist";

    @Autowired
    private PublicationRepository publicationRepository;

@Autowired
private RestTemplate restTemplate;

    @Override
    public List<Publication> getAll() {
        return publicationRepository.findAll();
    }

    @Override
    public Page<Publication> getAll(Pageable pageable) {
        return publicationRepository.findAll(pageable);
    }

    @Override
    public Publication getById(Long publicationId) {
        return publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, publicationId));
    }

    @Override
    public Publication create(Long artistId, Publication request) {


        boolean check= restTemplate.getForObject("http://localhost:8001/api/v1/artists/check/"+artistId,boolean.class);
        if(check){

            Date date = new Date();
            request.setArtistid(artistId);
            request.setRegisterdate(date);
            request.setLikes((long)0);
            return publicationRepository.save(request);
        }else {
            throw  new ResourcePerzonalized("id inexistente");
        }




    }

    @Override
    public Publication update(Long publicationId, Publication request) {

        return publicationRepository.findById(publicationId).map(post->{

            post.setLikes(request.getLikes());
            publicationRepository.save(post);
            return post;

        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, publicationId));




    }

    @Override
    public List<Publication> getPublicationByArtistId(Long artistId) {
        return publicationRepository.findByArtistid(artistId);
    }

    @Override
    public ResponseEntity<?> delete(Long publicationId) {
        return publicationRepository.findById(publicationId).map(post -> {
            publicationRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, publicationId));
    }




}
