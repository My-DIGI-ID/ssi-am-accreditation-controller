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

package com.bka.ssi.controller.accreditation.company.application.utilities;

import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileFormatException;
import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

/**
 * The type Csv builder.
 */
@Component
public class CsvBuilder {

    private final Logger logger;

    /**
     * Instantiates a new Csv builder.
     *
     * @param logger the logger
     */
    public CsvBuilder(Logger logger) {
        this.logger = logger;
    }

    /**
     * Read csv to list by column name list.
     *
     * @param <T>         the type parameter
     * @param inputStream the input stream
     * @param clazz       the clazz
     * @param separator   the separator
     * @return the list
     * @throws IOException             the io exception
     * @throws InvalidCsvFileException the invalid csv file exception
     */
    public <T> List<T> readCsvToListByColumnName(InputStream inputStream,
        Class clazz, char separator) throws IOException, InvalidCsvFileException {

        List<T> output;
        try {
            Reader reader = new InputStreamReader(inputStream);

            HeaderColumnNameMappingStrategy headerColumnNameMappingStrategy =
                new HeaderColumnNameMappingStrategy();
            headerColumnNameMappingStrategy.setType(clazz);

            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                .withType(clazz)
                .withMappingStrategy(headerColumnNameMappingStrategy)
                .withSeparator(separator)
                .withIgnoreEmptyLine(true)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

            output = csvToBean.parse();

            reader.close();
        } catch (RuntimeException e) {
            this.logger.error(e.getMessage());
            throw new InvalidCsvFileException();
        }

        return output;
    }

    /**
     * Read csv to list by column position list.
     *
     * @param <T>         the type parameter
     * @param inputStream the input stream
     * @param clazz       the clazz
     * @param separator   the separator
     * @return the list
     * @throws IOException             the io exception
     * @throws InvalidCsvFileException the invalid csv file exception
     */
    public <T> List<T> readCsvToListByColumnPosition(InputStream inputStream,
        Class clazz, char separator) throws IOException, InvalidCsvFileException {

        List<T> output;
        try {
            Reader reader = new InputStreamReader(inputStream);

            ColumnPositionMappingStrategy columnPositionMappingStrategy =
                new ColumnPositionMappingStrategy();
            columnPositionMappingStrategy.setType(clazz);

            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                .withType(clazz)
                .withMappingStrategy(columnPositionMappingStrategy)
                .withSeparator(separator)
                .withSkipLines(1)
                .withIgnoreEmptyLine(true)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

            output = csvToBean.parse();

            reader.close();
        } catch (RuntimeException e) {
            this.logger.error(e.getMessage());
            throw new InvalidCsvFileException();
        }

        return output;
    }

    /**
     * Gets header from dto.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @param dto   the dto
     * @return the header from dto
     * @throws InvalidCsvFileFormatException the invalid csv file format exception
     */
    public <T> List<String> getHeaderFromDto(Class clazz, T dto)
        throws InvalidCsvFileFormatException {
        try {
            HeaderColumnNameMappingStrategy headerColumnNameMappingStrategy =
                new HeaderColumnNameMappingStrategy();
            headerColumnNameMappingStrategy.setType(clazz);

            String[] headerArray = headerColumnNameMappingStrategy.generateHeader(dto);
            List<String> headerList = Arrays.asList(headerArray);

            return headerList;
        } catch (CsvRequiredFieldEmptyException e) {
            this.logger.error(e.getMessage());
            throw new InvalidCsvFileFormatException("Required field is empty");
        }
    }

    /**
     * Gets header from csv.
     *
     * @param inputStream the input stream
     * @return the header from csv
     * @throws IOException                   the io exception
     * @throws InvalidCsvFileFormatException the invalid csv file format exception
     */
    public List<String> getHeaderFromCsv(InputStream inputStream)
        throws IOException, InvalidCsvFileFormatException {
        try {
            Reader reader = new InputStreamReader(inputStream);

            CSVReader csvReader = new CSVReader(reader);
            CSVIterator csvIterator = new CSVIterator(csvReader);

            if (!csvIterator.hasNext()) {
                throw new InvalidCsvFileFormatException("CSV is empty");
            }
            String[] headerArray = csvIterator.next();
            List<String> headerList = Arrays.asList(headerArray);

            reader.close();
            return headerList;
        } catch (CsvValidationException e) {
            this.logger.error(e.getMessage());
            throw new InvalidCsvFileFormatException();
        }
    }

    /**
     * Validate header boolean.
     *
     * @param expectedHeader the expected header
     * @param actualHeader   the actual header
     * @return the boolean
     * @throws InvalidCsvFileFormatException the invalid csv file format exception
     */
    public boolean validateHeader(List<String> expectedHeader, List<String> actualHeader)
        throws InvalidCsvFileFormatException {
        boolean isValid = expectedHeader.equals(actualHeader);

        if (!isValid) {
            throw new InvalidCsvFileFormatException("CSV Header invalid");
        }

        return isValid;
    }
}
