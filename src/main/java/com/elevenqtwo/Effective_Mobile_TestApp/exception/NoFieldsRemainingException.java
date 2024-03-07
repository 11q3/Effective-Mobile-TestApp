package com.elevenqtwo.Effective_Mobile_TestApp.exception;

public class NoFieldsRemainingException extends RuntimeException {
    public NoFieldsRemainingException(String fieldType) {
        super("Cannot delete all " + fieldType + " fields. At least one field must remain.");
    }
}