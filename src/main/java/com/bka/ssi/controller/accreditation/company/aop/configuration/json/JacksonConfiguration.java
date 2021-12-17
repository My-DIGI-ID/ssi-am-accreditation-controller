/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * The type Jackson configuration.
 */
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
