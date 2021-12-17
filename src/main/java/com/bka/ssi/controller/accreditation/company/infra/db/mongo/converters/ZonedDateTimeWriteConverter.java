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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * The type Zoned date time write converter.
 */
@Component
@WritingConverter
public class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, Date> {

    @Override
    public Date convert(ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }
}