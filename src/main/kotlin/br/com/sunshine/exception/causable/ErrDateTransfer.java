package br.com.sunshine.exception.causable;

import org.springframework.http.HttpStatus;

public class ErrDateTransfer extends RuntimeException {

    private final HttpStatus status;

    public ErrDateTransfer(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
