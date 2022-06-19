package com.example.userservice.models.Artist;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CreateArtistResource {

    private String username;

    private String realname;

    private String lastname;

    private String email;

    private String password;

    private Long artistfollowers;


    private Set<String> roles=new HashSet<>();
}
