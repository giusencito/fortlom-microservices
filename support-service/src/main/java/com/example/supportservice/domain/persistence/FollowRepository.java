package com.example.supportservice.domain.persistence;


import com.example.supportservice.domain.model.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findByFanaticid(Long FanaticId);
    List<Follow> findByArtistid(Long ArtistId);
}