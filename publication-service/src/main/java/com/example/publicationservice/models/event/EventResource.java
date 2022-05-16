package com.example.publicationservice.models.event;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
public class EventResource {

    private Long id;


    private String description;



    private Long likes;


    private Date registerdate;



    private Long artistid;


    private String name;



    private String ticketLink;



    private Date releasedDate;
}
