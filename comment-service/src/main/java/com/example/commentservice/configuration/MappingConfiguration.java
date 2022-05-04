package com.example.commentservice.configuration;


import com.example.commentservice.mapping.ForumCommentMapper;
import com.example.commentservice.mapping.PublicationCommentMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("FortlomCommentServiceMappingConfiguration")
public class MappingConfiguration {

    @Bean
    public PublicationCommentMapper publicationCommentMapper(){
        return new PublicationCommentMapper();
    }

    @Bean
    public ForumCommentMapper forumcommentMapper(){ return new ForumCommentMapper(); }
}
