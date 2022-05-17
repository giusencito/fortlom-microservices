package com.example.publicationservice.api;

import com.example.publicationservice.domain.service.MultimediaService;
import com.example.publicationservice.mapping.MultimediaMapper;
import com.example.publicationservice.models.multimedia.MultimediaResource;
import com.example.publicationservice.util.ImageModel;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity.BodyBuilder;

@RestController
@RequestMapping("/api/v1/contentservice")
public class MultimediaController {


    @Autowired
    private MultimediaService multimediaService;

    @Autowired
    private MultimediaMapper mapper;

    @Autowired
    private ModelMapper mapping;


    @GetMapping("/multimedias")
    public Page<MultimediaResource> getAllMultimedias(Pageable pageable) {
        return mapper.modelListToPage(multimediaService.getAll(), pageable);
    }


    @GetMapping("/multimedias/image/info/{multimediaId}")
    public ImageModel getImageDetails (@PathVariable("multimediaId") Long multimediaId) throws IOException{
        return  multimediaService.getImageDetails(multimediaId);
    }



    @PostMapping("/publications/{publicationId}/multimedias")
    public void createComment( @PathVariable Long publicationId,@RequestParam("file") MultipartFile file) throws IOException {
        multimediaService.create(publicationId,file);
    }

    @GetMapping("/publications/{publicationId}/multimedias")
    public List<ImageModel> getAllmultimediasByPublicationId(@PathVariable Long publicationId, Pageable pageable) {
        return multimediaService.getMultimediaByPublicationId(publicationId);
    }





    @DeleteMapping("/multimedias/{multimediaId}")
    public ResponseEntity<?> deleteMultimedia(@PathVariable Long commentId) {
        return multimediaService.delete(commentId);
    }


}
