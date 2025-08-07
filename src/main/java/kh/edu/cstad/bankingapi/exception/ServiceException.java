package kh.edu.cstad.bankingapi.exception;

import org.springframework.http.HttpStatus;
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
    @ExceptionHandler(ConfirmPasswordException.class)
    public ResponseEntity<Object> handleConfirmPasswordMatch(ConfirmPasswordException ex){
        ErrorResponse<String> error = ErrorResponse.<String>builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .details("🤦‍♂️ ចប់")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }


}
