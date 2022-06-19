package com.example.userservice.domain.service;

import com.example.userservice.domain.model.entity.Person;

public interface AdminService {


    Person ban(Long personId);
}
