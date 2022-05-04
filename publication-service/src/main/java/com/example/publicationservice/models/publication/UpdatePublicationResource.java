package com.example.publicationservice.models.publication;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdatePublicationResource {
    private String publicationdescription;
    private Long likes;
    private Date date;
}