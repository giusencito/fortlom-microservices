package com.example.publicationservice.service;

import com.example.publicationservice.domain.model.entity.Multimedia;
import com.example.publicationservice.domain.persistence.MultimediaRepository;
import com.example.publicationservice.domain.persistence.PublicationRepository;
import com.example.publicationservice.domain.service.MultimediaService;
import com.example.publicationservice.shared.execption.ResourceNotFoundException;
import com.example.publicationservice.util.ImageModel;
import com.example.publicationservice.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validator;
import java.io.IOException;
import java.util.*;

@Service
public class MultimediaServiceImpl implements MultimediaService {






    private static final String ENTITY = "Multimedia";
    @Autowired
    private MultimediaRepository multimediaRepository;
    @Autowired
    private PublicationRepository publicationRepository;







    @Override
    public List<Multimedia> getAll() {
        return multimediaRepository.findAll();
    }

    @Override
    public Page<Multimedia> getAll(Pageable pageable) {
        return multimediaRepository.findAll(pageable);
    }



    @Override
    public ImageModel getImageDetails(Long MultimediaID) {
        Optional<Multimedia>dbImage=multimediaRepository.findById(MultimediaID);
        ImageModel imageModel= new ImageModel(dbImage.get().getId(),dbImage.get().getType(), ImageUtility.decompressImage(dbImage.get().getContent()));
        return imageModel;
    }



    @Override
    public void create(Long publicationId, MultipartFile file) throws IOException {
        Multimedia request = new Multimedia();
        request.setType(file.getContentType());

        request.setContent(ImageUtility.compressImage(file.getBytes()));

        publicationRepository.findById(publicationId)
                .map(publications -> {
                    request.setPublication(publications);
                    multimediaRepository.save(request);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Publication", publicationId));


    }






    @Override
    public List<ImageModel> getMultimediaByPublicationId(Long multimediaId) {
        List<Multimedia> multimedias=multimediaRepository.findByPublicationId(multimediaId);
        List<ImageModel>imageModels = new ArrayList<>() ;
        for (int i=0;i<multimedias.size();i++){
            Multimedia multimedia=multimedias.get(i);
            ImageModel imageModel= new ImageModel(multimedia.getId(),multimedia.getType(),ImageUtility.decompressImage(multimedia.getContent()));
            imageModels.add(imageModel);
        }
        return imageModels;
    }

    @Override
    public ResponseEntity<?> delete(Long multimediaId) {
        return multimediaRepository.findById(multimediaId).map(post -> {
            multimediaRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, multimediaId));
    }
}
