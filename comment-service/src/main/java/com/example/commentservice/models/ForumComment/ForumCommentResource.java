package com.example.commentservice.models.ForumComment;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ForumCommentResource {

    private Long id;

    private String commentdescription;

    private Date registerdate;

    private Long personid;

    private Long forumid;
}
