package com.example.publicationservice.domain.persistence;

import com.example.publicationservice.domain.model.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication,Long>{
    List<Publication> findByArtistid(Long artistId);

    boolean existsById(Long publicationId);

}
