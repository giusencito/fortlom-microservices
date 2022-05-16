package com.example.publicationservice.models.event;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateEventResource {

    private String description;

    private String name;



    private String ticketLink;



    private Date releasedDate;
}
