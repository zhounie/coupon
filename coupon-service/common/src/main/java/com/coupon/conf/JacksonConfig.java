package com.coupon.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper() ;
        objectMapper.setDateFormat(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        );
        return objectMapper;
    }

}
