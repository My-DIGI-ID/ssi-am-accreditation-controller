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

package com.bka.ssi.controller.accreditation.company.aop.logging;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
public class LoggingFacadeTest {

    private final Logger logger = LoggerFactory.getLogger(LoggingFacadeTest.class);

    private TestService proxy;

    @BeforeEach
    public void init() {
        AspectJProxyFactory factory = new AspectJProxyFactory(new LoggingFacadeTest.TestService());
        factory.addAspect(new LoggingFacade(logger));
        proxy = factory.getProxy();
    }

    @Test
    void log(CapturedOutput output) {
        proxy.someMethod();

        Assertions.assertTrue(output.getOut().contains(
            "start: method someMethod in class com.bka.ssi.controller.accreditation.company.aop.logging.LoggingFacadeTest$TestService"));
        Assertions.assertTrue(output.getOut().contains(
            "end: method someMethod in class com.bka.ssi.controller.accreditation.company.aop.logging.LoggingFacadeTest$TestService"));
    }

    public class TestService {

        @LoggingOperation(level = LoggingUtility.Level.INFO)
        public void someMethod() {
            return;
        }
    }
}
