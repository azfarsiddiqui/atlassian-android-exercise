package com.atlassian.exercise.exceptions;

public class NetworkNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "No Wifi or data connection found";
    }
}
