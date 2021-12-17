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

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * The type Logging utility.
 * ToDo: Currently unused
 */
public class LoggingUtility {

    /**
     * The enum Level.
     */
    public static enum Level {
        /**
         * Trace level.
         */
        TRACE,
        /**
         * Debug level.
         */
        DEBUG,
        /**
         * Info level.
         */
        INFO,
        /**
         * Warn level.
         */
        WARN,
        /**
         * Error level.
         */
        ERROR
    }

    private LoggingUtility() {
    }

    /**
     * Log.
     *
     * @param logger the logger
     * @param level  the level
     * @param txt    the txt
     */
    public static void log(Logger logger, Level level, String txt) {
        if (logger != null && level != null) {
            switch (level) {
                case TRACE:
                    logger.trace(txt);
                    break;
                case DEBUG:
                    logger.debug(txt);
                    break;
                case INFO:
                    logger.info(txt);
                    break;
                case WARN:
                    logger.warn(txt);
                    break;
                case ERROR:
                    logger.error(txt);
                    break;
            }
        }
    }

    /**
     * Log.
     *
     * @param logger the logger
     * @param level  the level
     * @param format the format
     * @param args   the args
     */
    public static void log(Logger logger, Level level, String format, Object[] args) {
        if (logger != null && level != null) {
            switch (level) {
                case TRACE:
                    logger.trace(format, args);
                    break;
                case DEBUG:
                    logger.debug(format, args);
                    break;
                case INFO:
                    logger.info(format, args);
                    break;
                case WARN:
                    logger.warn(format, args);
                    break;
                case ERROR:
                    logger.error(format, args);
                    break;
            }
        }
    }

    /**
     * Log.
     *
     * @param logger    the logger
     * @param level     the level
     * @param txt       the txt
     * @param throwable the throwable
     */
    public static void log(Logger logger, Level level, String txt, Throwable throwable) {
        if (logger != null && level != null) {
            switch (level) {
                case TRACE:
                    logger.trace(txt, throwable);
                    break;
                case DEBUG:
                    logger.debug(txt, throwable);
                    break;
                case INFO:
                    logger.info(txt, throwable);
                    break;
                case WARN:
                    logger.warn(txt, throwable);
                    break;
                case ERROR:
                    logger.error(txt, throwable);
                    break;
            }
        }
    }

    /**
     * Log rest error response.
     *
     * @param logger    the logger
     * @param response  the response
     * @param throwable the throwable
     */
    public static void logRestErrorResponse(Logger logger, RestErrorResponse response,
        Throwable throwable) {
        if (logger != null) {

            if (response != null) {
                logger.error("Response: {}", response);
            }

            if (throwable != null) {
                logger.error("Exception: {}", throwable.getClass().getName());
                logger.debug("Message: {}", throwable.getMessage());
            }

            if (throwable != null && throwable.getStackTrace() != null) {
                StringBuilder builder = new StringBuilder();
                Arrays.stream(throwable.getStackTrace())
                    .forEach(stackTraceElement -> builder.append(stackTraceElement).append("\n"));
                logger.debug("Stack trace: {}", builder);
            }
        }
    }
}
