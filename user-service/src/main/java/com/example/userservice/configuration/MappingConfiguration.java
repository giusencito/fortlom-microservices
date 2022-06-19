package com.example.userservice.configuration;
import com.example.userservice.mapping.ArtistMapper;
import com.example.userservice.mapping.FanaticMapper;
import com.example.userservice.mapping.PersonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("FortlomUserServiceMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public FanaticMapper fanaticMapper() {
        return new FanaticMapper();
    }

    @Bean
    public ArtistMapper artistMapper() {
        return new ArtistMapper();
    }

    @Bean
    public PersonMapper personMapper() {
        return new PersonMapper();
    }
}
