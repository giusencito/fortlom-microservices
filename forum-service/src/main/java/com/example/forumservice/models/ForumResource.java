package com.example.forumservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForumResource {
    private Long id;
    private String forumname;
    private String forumdescription;
    private Long personid;
}

