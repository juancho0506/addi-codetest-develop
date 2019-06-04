package com.addi.codetest.addicodetestdevelop.exception;

public class CandidateValidationException extends Exception {
    public CandidateValidationException() {
        super();
    }

    public CandidateValidationException(String message) {
        super(message);
    }

    public CandidateValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
