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

        boolean check1 = restTemplate.getForObject("http://user-service/api/v1/userservice/artists/check/" + userId,boolean.class);
        boolean check2 = restTemplate.getForObject("http://user-service/api/v1/userservice/fanatics/check/" + userId,boolean.class);
        boolean check3=  restTemplate.getForObject("http://forum-service/api/v1/check/" + forumId,boolean.class);

        if(check1 && check3||check2 && check3){
            Date date = new Date();
            request.setForumid(forumId);
            request.setPersonid(userId);
            request.setRegisterdate(date);
            return forumcommentRepository.save(request);
        }else{

            throw  new ResourcePerzonalized("id inexistente");


        }




    }


    @Override
    public List<ForumComment> getForumCommentByForumId(Long forumId) {

        boolean check=  restTemplate.getForObject("http://forum-service/api/v1/check/" + forumId,boolean.class);
        if(check) {
            return forumcommentRepository.findByForumid(forumId);
        }else {
            throw  new ResourcePerzonalized("id inexistente");

        }
    }

    @Override
    public ResponseEntity<?> delete(Long forumcommentId) {
        return forumcommentRepository.findById(forumcommentId).map(forumComment -> {
            forumcommentRepository.delete(forumComment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, forumcommentId));
    }






}
