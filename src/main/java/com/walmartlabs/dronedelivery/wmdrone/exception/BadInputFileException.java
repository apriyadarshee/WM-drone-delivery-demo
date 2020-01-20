package com.walmartlabs.dronedelivery.wmdrone.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Issue with Input file exception class.
 */
public class BadInputFileException extends Exception {

    Logger logger = LoggerFactory.getLogger(BadInputFileException.class);

    public BadInputFileException(final String message) {
        super(message);
        logger.error(message);
    }
}