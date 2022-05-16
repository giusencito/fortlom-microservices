package com.example.userservice.domain.persistence;

import com.example.userservice.domain.model.entity.Fanatic;
import com.example.userservice.domain.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FanaticRepository extends JpaRepository<Fanatic,Long>{
    Optional<Person> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    boolean existsById(Long fanaticId);

    Optional<Person> findByUsernameOrEmail(String username,String email);
}