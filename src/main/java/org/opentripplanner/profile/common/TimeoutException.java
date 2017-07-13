package org.opentripplanner.profile.common;

/**
 * Created by melvi on 13-7-2017.
 */
public class TimeoutException extends Exception {

    private static final long serialVersionUID = 1L;

    public String message;

    public TimeoutException(String message) {
        this.message = message;
    }
}

