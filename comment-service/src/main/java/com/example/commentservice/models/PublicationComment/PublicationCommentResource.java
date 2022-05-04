package com.example.commentservice.models.PublicationComment;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PublicationCommentResource {
    private Long id;

    private String commentdescription;

    private Date registerdate;

    private Long personid;

    private Long publicationid;
}
