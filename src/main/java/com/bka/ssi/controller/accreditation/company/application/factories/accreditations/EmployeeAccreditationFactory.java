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

package com.bka.ssi.controller.accreditation.company.application.factories.accreditations;

import com.bka.ssi.controller.accreditation.company.application.factories.AccreditationFactory;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * The type Employee accreditation factory.
 */
@Component
public class EmployeeAccreditationFactory
    implements AccreditationFactory<Employee, EmployeeAccreditation> {

    private final Logger logger;

    /**
     * Instantiates a new Employee accreditation factory.
     *
     * @param logger the logger
     */
    public EmployeeAccreditationFactory(Logger logger) {
        this.logger = logger;
    }

    @Override
    public EmployeeAccreditation create(Employee employee, String userName) {
        return new EmployeeAccreditation(employee, EmployeeAccreditationStatus.OPEN, userName,
            ZonedDateTime.now());
    }
}
