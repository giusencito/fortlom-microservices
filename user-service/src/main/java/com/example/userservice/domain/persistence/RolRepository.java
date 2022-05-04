package com.example.userservice.domain.persistence;

import com.example.userservice.domain.model.entity.Rol;
import com.example.userservice.domain.model.enumeration.RolName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByName(RolName name);
    boolean existsByName(RolName name);
}
