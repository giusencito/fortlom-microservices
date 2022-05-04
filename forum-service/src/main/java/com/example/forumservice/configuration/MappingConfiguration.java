package com.example.forumservice.configuration;


import com.example.forumservice.mapping.ForumMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("FortlomForumServiceMappingConfiguration")
public class MappingConfiguration {


    @Bean
    public ForumMapper forumMapper() {
        return new ForumMapper();
    }
}
