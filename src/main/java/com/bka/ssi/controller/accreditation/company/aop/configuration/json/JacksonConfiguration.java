package com.bka.ssi.controller.accreditation.company.aop.configuration.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class JacksonConfiguration implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(
        List<HttpMessageConverter<?>> converters) {

        converters.stream().filter(c -> c instanceof MappingJackson2HttpMessageConverter)
            .forEach(c -> {
                ObjectMapper mapper = ((MappingJackson2HttpMessageConverter) c).getObjectMapper();
                
                mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
                mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            });
    }
}
