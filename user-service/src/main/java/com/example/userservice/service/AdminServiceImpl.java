package com.example.userservice.service;

import com.example.userservice.domain.model.entity.Person;
import com.example.userservice.domain.persistence.ArtistRepository;
import com.example.userservice.domain.persistence.PersonRepository;
import com.example.userservice.domain.service.AdminService;
import com.example.userservice.shared.execption.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {

    private static final String ENTITY = "Admin";

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person ban(Long personId) {
        return personRepository.findById(personId).map(post->{

            post.setBann(true);
            return post;

        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, personId));
    }
}
