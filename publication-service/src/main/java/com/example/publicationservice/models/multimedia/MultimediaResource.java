package com.example.publicationservice.models.multimedia;

import com.example.publicationservice.models.publication.PublicationResource;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MultimediaResource {

    private Long id;
    private byte[] content;
    private PublicationResource publication;}
