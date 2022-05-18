package com.example.supportservice.service;


import com.example.supportservice.domain.model.entity.Rate;
import com.example.supportservice.domain.persistence.RateRepository;
import com.example.supportservice.domain.service.RateService;
import com.example.supportservice.shared.execption.ResourceNotFoundException;
import com.example.supportservice.shared.execption.ResourcePerzonalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import javax.validation.Validator;
@Service
public class RateServiceImpl implements RateService {


    private static final String ENTITY = "Rate";
    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RestTemplate restTemplate;

    public RateServiceImpl() {

    }


    @Override
    public List<Rate> getAll() {
        return rateRepository.findAll();
    }

    @Override
    public Page<Rate> getAll(Pageable pageable) {
        return rateRepository.findAll(pageable);
    }

    @Override
    public Rate getById(Long rateId) {
        return rateRepository.findById(rateId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, rateId));
    }

    @Override
    public Rate create(Long FanaticId, Long ArtistId, Rate request) {
        boolean check1= restTemplate.getForObject("http://user-service/api/v1/userservice/artists/check/"+ArtistId,boolean.class);
        boolean check2= restTemplate.getForObject("http://user-service/api/v1/userservice/fanatics/check/"+FanaticId,boolean.class);
        if(check1&&check2){

            request.setArtistid(ArtistId);
            request.setFanaticid(FanaticId);
            return rateRepository.save(request);


        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }




    }

    @Override
    public Rate update(Long rateId, Rate request) {

        return rateRepository.findById(rateId).map(post->{
            request.setRates(request.getRates());
            rateRepository.save(post);
            return post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, rateId));



    }

    @Override
    public List<Rate> ratesByFanaticId(Long FanaticId) {
        boolean check1= restTemplate.getForObject("http://user-service/api/v1/userservice/fanatics/check/"+FanaticId,boolean.class);
        if(check1)
        return rateRepository.findByFanaticid(FanaticId);
        else throw  new ResourcePerzonalized("id inexistente de Fanatico");
    }

    @Override
    public List<Rate> ratesByArtistId(Long ArtistId) {
        boolean check1= restTemplate.getForObject("http://user-service/api/v1/userservice/artists/check/"+ArtistId,boolean.class);
        if(check1)
        return rateRepository.findByArtistid(ArtistId);
        else throw new ResourcePerzonalized("id inexistente de Artista");
    }

    @Override
    public ResponseEntity<?> delete(Long rateId) {
        return rateRepository.findById(rateId).map(post -> {
            rateRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, rateId));
    }
}
