package com.example.commentservice.domain.persistence;

import com.example.commentservice.domain.model.entity.PublicationComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationCommentRepository extends JpaRepository<PublicationComment,Long>{
    List<PublicationComment> findByPublicationid(Long PublicationId);
    List<PublicationComment> findByPersonid (Long UserId);
}
