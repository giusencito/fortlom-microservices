package com.example.forumservice.service;

import com.example.forumservice.domain.model.entity.Forum;
import com.example.forumservice.domain.persistence.ForumRepository;
import com.example.forumservice.domain.service.ForumService;
import com.example.forumservice.shared.execption.ResourceNotFoundException;
import com.example.forumservice.shared.execption.ResourcePerzonalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {


    private static final String ENTITY = "Forum";
    //private static final String ENTITY2 = "User";


    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }

    @Override
    public Page<Forum> getAllForums(Pageable pageable) {
        return forumRepository.findAll(pageable);
    }

    @Override
    public Forum getForumById(Long ForumId) {
        return forumRepository.findById(ForumId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, ForumId));
    }

    @Override
    public Forum createForum(Long userId, Forum request) {

            request.setPersonid(userId);
            return forumRepository.save(request);

    }



    @Override
    public List<Forum> getForumsByUserId(Long userId) {

        return forumRepository.findByPersonid(userId);

    }

    @Override
    public ResponseEntity<?> deleteForum(Long forumId) {
        return forumRepository.findById(forumId).map(Forum -> {
            forumRepository.delete(Forum);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, forumId));
    }

    @Override
    public boolean existsforum(Long forumId) {



        return forumRepository.existsById(forumId);
    }
}
