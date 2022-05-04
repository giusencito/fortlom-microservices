package com.example.publicationservice.domain.persistence;

import com.example.publicationservice.domain.model.entity.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MultimediaRepository  extends JpaRepository<Multimedia,Long>{
    List<Multimedia> findByPublicationId(Long publicationId);
}
