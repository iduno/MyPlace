package com.air.advantage.libraryairconlightjson;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonFuncs {

    private static final Logger LOGGER = Logger.getLogger(CommonFuncs.class.getName());

    public static void logException(Exception exception) {
        logException(exception, "");
    }

    public static void logFatalException(Exception exception) {
        logFatalException(exception, "");
    }

    public static void logException(Exception exception, String errorMessage) {
        logException(exception, errorMessage, false);
    }

    public static void logFatalException(Exception exception, String errorMessage) {
        logException(exception, errorMessage, true);
    }

    private static void logException(Exception exception, String errorMessage, boolean isFatal) {
        if (exception == null) {
            throw new NullPointerException("exception is null");
        }
        if (errorMessage == null) {
            throw new NullPointerException("errorMessage is null");
        }

        if (isFatal) {
            LOGGER.log(Level.SEVERE, errorMessage, exception);
        } else {
            LOGGER.log(Level.WARNING, errorMessage, exception);
        }
    }
}