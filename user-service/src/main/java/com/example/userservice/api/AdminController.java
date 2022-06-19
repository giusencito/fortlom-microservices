package com.example.userservice.api;


import com.example.userservice.domain.service.AdminService;
import com.example.userservice.domain.service.ArtistService;
import com.example.userservice.mapping.ArtistMapper;
import com.example.userservice.mapping.PersonMapper;
import com.example.userservice.models.Artist.ArtistResource;
import com.example.userservice.models.PersonResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userservice/admins")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @Autowired
    private PersonMapper mapper;


    @PutMapping("banuser/{UserId}")
    public PersonResource getBan(@PathVariable("UserId") Long UserId) {
        return mapper.toResource(adminService.ban(UserId));
    }



}
