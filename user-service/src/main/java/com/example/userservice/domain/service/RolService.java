package com.example.userservice.domain.service;
import com.example.userservice.domain.model.entity.Rol;
import com.example.userservice.domain.model.enumeration.RolName;

import java.util.List;
import java.util.Optional;
public interface RolService {
    Optional<Rol> findByName(RolName name);
    List<Rol> getAll();
}
