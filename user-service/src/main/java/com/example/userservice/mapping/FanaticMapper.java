package com.example.userservice.mapping;

import com.example.userservice.domain.model.entity.Fanatic;
import com.example.userservice.models.Fanatic.CreateFanaticResource;
import com.example.userservice.models.Fanatic.FanaticResource;
import com.example.userservice.models.Fanatic.UpdateFanaticResource;
import com.example.userservice.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.io.Serializable;
import java.util.List;
public class FanaticMapper implements Serializable{


    @Autowired
    EnhancedModelMapper mapper;

    public FanaticResource toResource(Fanatic model) {
        return mapper.map(model, FanaticResource.class);
    }

    public Page<FanaticResource> modelListToPage(List<Fanatic> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, FanaticResource.class), pageable, modelList.size());
    }
    public Fanatic toModel(CreateFanaticResource resource) {
        return mapper.map(resource, Fanatic.class);
    }

    public Fanatic toModel(UpdateFanaticResource resource) {
        return mapper.map(resource, Fanatic.class);
    }
}
