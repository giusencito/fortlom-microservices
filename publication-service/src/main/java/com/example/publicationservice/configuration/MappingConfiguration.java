package com.example.publicationservice.configuration;
import com.example.publicationservice.mapping.MultimediaMapper;
import com.example.publicationservice.mapping.PublicationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("FortlomPublicationServiceMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public PublicationMapper publicationMapper(){
        return new PublicationMapper();
    }
    @Bean
    public MultimediaMapper multimediaMapper(){ return new MultimediaMapper(); }
}
