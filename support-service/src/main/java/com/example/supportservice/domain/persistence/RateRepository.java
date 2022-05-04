package com.example.supportservice.domain.persistence;


import com.example.supportservice.domain.model.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface RateRepository extends JpaRepository<Rate,Long> {

    List<Rate> findByFanaticid(Long FanaticId);
    List<Rate> findByArtistid(Long ArtistId);


}
