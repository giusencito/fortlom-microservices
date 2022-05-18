package com.example.reportservice.configuration;

import com.example.publicationservice.mapping.EventMapper;
import com.example.publicationservice.mapping.MultimediaMapper;
import com.example.publicationservice.mapping.PublicationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("FortlomReportServiceMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public ReportMapper reportMapper(){
        return new ReportMapper();
    }

}
