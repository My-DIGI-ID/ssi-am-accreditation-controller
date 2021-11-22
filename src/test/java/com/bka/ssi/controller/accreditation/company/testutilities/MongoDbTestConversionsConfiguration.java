package com.bka.ssi.controller.accreditation.company.testutilities;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.converters.ZonedDateTimeReadConverter;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.converters.ZonedDateTimeWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoDbTestConversionsConfiguration {

    @Primary
    @Bean
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ZonedDateTimeWriteConverter());
        converters.add(new ZonedDateTimeReadConverter());
        return new MongoCustomConversions(converters);
    }
}
