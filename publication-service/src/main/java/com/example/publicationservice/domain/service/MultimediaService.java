package com.example.publicationservice.domain.service;
import com.example.publicationservice.domain.model.entity.Multimedia;
import com.example.publicationservice.util.ImageModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface MultimediaService {
    List<Multimedia> getAll();
    Page<Multimedia> getAll(Pageable pageable);
    ImageModel getImageDetails(Long MultimediaID);
    void create(Long multimediaId, MultipartFile file) throws IOException;
    List<ImageModel> getMultimediaByPublicationId(Long multimediaId);
    ResponseEntity<?> delete(Long commentId);

}
