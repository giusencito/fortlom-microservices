package com.example.commentservice.models.PublicationComment;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreatePublicationComment {
    private String commentdescription;

    private Date registerdate;
}
