package dev.taufiksty.main.run;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RunException extends RuntimeException {

    private final HttpStatus status;

    public RunException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class RunNotFoundException extends RunException {
        public RunNotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }
    }
}
