package com.example.supportservice.service;


import com.example.supportservice.domain.model.entity.Rate;
import com.example.supportservice.domain.persistence.RateRepository;
import com.example.supportservice.domain.service.RateService;
import com.example.supportservice.shared.execption.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.validation.Validator;
@Service
public class RateServiceImpl implements RateService {


    private static final String ENTITY = "Rate";
    @Autowired
    private RateRepository rateRepository;



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

                    request.setArtistid(ArtistId);
                    request.setFanaticid(FanaticId);
                    return rateRepository.save(request);

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
        return rateRepository.findByFanaticid(FanaticId) ;   }

    @Override
    public List<Rate> ratesByArtistId(Long ArtistId) {
        return rateRepository.findByArtistid(ArtistId);
    }

    @Override
    public ResponseEntity<?> delete(Long rateId) {
        return rateRepository.findById(rateId).map(post -> {
            rateRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, rateId));
    }
}
