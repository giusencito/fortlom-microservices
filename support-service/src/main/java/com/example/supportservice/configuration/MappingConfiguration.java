package com.example.supportservice.configuration;

import com.example.supportservice.mapping.FollowMapper;
import com.example.supportservice.mapping.RateMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("FortlomSupportServiceMappingConfiguration")
public class MappingConfiguration {

    @Bean
    public FollowMapper followMapper(){ return new FollowMapper(); }


    @Bean
    public RateMapper rateMapper(){ return new RateMapper(); }
}
