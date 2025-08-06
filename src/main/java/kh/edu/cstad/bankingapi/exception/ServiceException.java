package kh.edu.cstad.bankingapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceException{

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex){

        ErrorResponse<String> error = ErrorResponse.<String>builder()
                .message(ex.getReason())
                .status(ex.getStatusCode().value())
                .timestamp(LocalDateTime.now())
                .details(ex.getReason())
                .build();

        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }


}
