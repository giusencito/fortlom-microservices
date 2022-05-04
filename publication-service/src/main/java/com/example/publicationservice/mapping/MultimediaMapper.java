package com.example.publicationservice.mapping;

import com.example.publicationservice.domain.model.entity.Multimedia;
import com.example.publicationservice.models.multimedia.MultimediaResource;
import com.example.publicationservice.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.io.Serializable;
import java.util.List;
public class MultimediaMapper implements Serializable{

    @Autowired
    EnhancedModelMapper mapper;

    public MultimediaResource toResource(Multimedia model) {
        return mapper.map(model, MultimediaResource.class);
    }

    public Page<MultimediaResource> modelListToPage(List<Multimedia> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, MultimediaResource.class), pageable, modelList.size());
    }



}