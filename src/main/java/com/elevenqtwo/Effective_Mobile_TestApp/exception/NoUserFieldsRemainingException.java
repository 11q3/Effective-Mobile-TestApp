package com.elevenqtwo.Effective_Mobile_TestApp.exception;

public class NoUserFieldsRemainingException extends RuntimeException {
    public NoUserFieldsRemainingException(String fieldType) {
        super("Cannot delete all " + fieldType + " fields. At least one field must remain.");
    }
}