package com.example.userservice.mapping;

import com.example.userservice.domain.model.entity.Fanatic;
import com.example.userservice.domain.model.entity.Person;
import com.example.userservice.models.Fanatic.FanaticResource;
import com.example.userservice.models.PersonResource;
import com.example.userservice.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonMapper {


    @Autowired
    EnhancedModelMapper mapper;

    public PersonResource toResource(Person model) {
        return mapper.map(model, PersonResource.class);
    }

}
