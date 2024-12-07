package org.api.model.error;

public class CustomClientError extends RuntimeException {
    public CustomClientError(String message) {
        super(message);
    }
}