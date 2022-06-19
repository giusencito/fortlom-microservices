package com.example.userservice.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonResource {
    private Long id;

    private String username;

    private String realname;

    private String lastname;

    private String email;

    private String password;

    private byte[] content;

    boolean bann;






}
