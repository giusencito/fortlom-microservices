package com.example.reportservice.configuration;

import com.example.reportservice.mapping.ReportMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("FortlomReportServiceMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public ReportMapper reportMapper(){
        return new ReportMapper();
    }

}
