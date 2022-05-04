package com.example.userservice.models.Artist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateArtistResource {
    private String UserName;

    private String realname;

    private String lastname;

    private String email;

    private String password;

    private Long artistfollowers;
    private String instagramLink;

    private String facebookLink;

    private String twitterLink;
}
