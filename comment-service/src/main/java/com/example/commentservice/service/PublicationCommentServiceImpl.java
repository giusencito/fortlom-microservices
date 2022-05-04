package com.example.commentservice.service;

import com.example.commentservice.domain.model.entity.PublicationComment;
import com.example.commentservice.domain.persistence.PublicationCommentRepository;
import com.example.commentservice.domain.service.PublicationCommentService;
import com.example.commentservice.shared.execption.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.validation.Validator;
@Service
public class PublicationCommentServiceImpl implements PublicationCommentService {

    private static final String ENTITY = "Comment";
    @Autowired
    private PublicationCommentRepository publicationCommentRepository;


    @Override
    public List<PublicationComment> getAll() {
        return publicationCommentRepository.findAll();
    }

    @Override
    public Page<PublicationComment> getAll(Pageable pageable) {
        return publicationCommentRepository.findAll(pageable);
    }

    @Override
    public PublicationComment getById(Long commentId) {
        return publicationCommentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, commentId));
    }

    @Override
    public PublicationComment create(Long userId, Long publicationId, PublicationComment request) {

        Date date = new Date();

        request.setPublicationid(publicationId);
        request.setPersonid(userId);
        request.setRegisterdate(date);
        return publicationCommentRepository.save(request);


    }



    @Override
    public List<PublicationComment> getCommentByPublicationId(Long publicationId) {
        return publicationCommentRepository.findByPublicationid(publicationId);
    }

    @Override
    public ResponseEntity<?> delete(Long commentId) {
        return publicationCommentRepository.findById(commentId).map(post -> {
            publicationCommentRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, commentId));
    }
}
