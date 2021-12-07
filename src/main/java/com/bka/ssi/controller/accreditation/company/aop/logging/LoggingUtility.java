package com.bka.ssi.controller.accreditation.company.aop.logging;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import org.slf4j.Logger;

import java.util.Arrays;

public class LoggingUtility {

    public static enum Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }

    private LoggingUtility() {
    }

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
