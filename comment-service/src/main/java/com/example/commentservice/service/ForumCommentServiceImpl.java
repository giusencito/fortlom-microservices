package com.example.commentservice.service;

import com.example.commentservice.domain.model.entity.ForumComment;
import com.example.commentservice.domain.persistence.ForumCommentRepository;
import com.example.commentservice.domain.service.ForumCommentService;
import com.example.commentservice.shared.execption.ResourceNotFoundException;
import com.example.commentservice.shared.execption.ResourcePerzonalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class ForumCommentServiceImpl implements ForumCommentService {


    private static final String ENTITY = "ForumComment";

    @Autowired
    private ForumCommentRepository forumcommentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<ForumComment> getAll() {
        return forumcommentRepository.findAll();
    }

    @Override
    public Page<ForumComment> getAll(Pageable pageable) {
        return forumcommentRepository.findAll(pageable);
    }

    @Override
    public ForumComment getById(Long forumcommentId) {
        return forumcommentRepository.findById(forumcommentId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, forumcommentId));
    }

    @Override
    public ForumComment create(Long userId, Long forumId, ForumComment request) {




            Date date = new Date();
            request.setForumid(forumId);
            request.setPersonid(userId);
            request.setRegisterdate(date);
            return forumcommentRepository.save(request);





    }


    @Override
    public List<ForumComment> getForumCommentByForumId(Long forumId) {


            return forumcommentRepository.findByForumid(forumId);

    }

    @Override
    public ResponseEntity<?> delete(Long forumcommentId) {
        return forumcommentRepository.findById(forumcommentId).map(forumComment -> {
            forumcommentRepository.delete(forumComment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, forumcommentId));
    }






}
