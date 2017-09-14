package com.epam.cm.core.logger;

import org.slf4j.Logger;

public final class LoggerFactory {

    private LoggerFactory() {
    }

    /**
     * Creates an instance of slf4j logger for specified class.
     *
     * @param targetClass
     *            target class for the logger.
     * @return logger instance.
     */
    public static Logger getLogger(final Class targetClass) {
        return org.slf4j.LoggerFactory.getLogger(targetClass);
    }

}
