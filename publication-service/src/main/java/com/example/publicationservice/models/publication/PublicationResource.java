package com.example.publicationservice.models.publication;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PublicationResource {
    private Long id;
    private String publicationdescription;
    private Long likes;
    private Date registerdate;
    private Long artistid;
}
