package com.elevenqtwo.Effective_Mobile_TestApp.exception;

public class UserDataDoesNotExistException extends Exception {
    public UserDataDoesNotExistException(String message) {
        super(message);
    }
}
