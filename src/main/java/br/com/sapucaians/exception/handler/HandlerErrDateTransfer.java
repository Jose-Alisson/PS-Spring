package br.com.sapucaians.exception.handler;

import br.com.sapucaians.exception.causable.ErrDateTransfer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerErrDateTransfer {

    @ExceptionHandler(ErrDateTransfer.class)
    public ResponseEntity<?> handle(ErrDateTransfer err){
        return ResponseEntity.status(err.getStatus()).body(err.getMessage());
    }
}