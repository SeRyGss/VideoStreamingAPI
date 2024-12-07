package org.api.model.error;

public class CustomServerError extends RuntimeException {
    public CustomServerError(String message) {
        super(message);
    }
}
