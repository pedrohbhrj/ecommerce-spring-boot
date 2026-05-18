package br.com.pedrohbhrj.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.List;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorDetails<?>> exceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("Internal server error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new CustomErrorDetails<>(
                        "INTERNAL SERVER ERROR",
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        null
                )
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomErrorDetails<?>> notFoundExceptionHandler(NotFoundException ex, HttpServletRequest request) {

        log.error("Entity was not found in the system: ", ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(new CustomErrorDetails<>(ex.getMessage(),
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.NOT_FOUND.value(),
                        null));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<CustomErrorDetails<?>> alreadyExistsExceptionHandler(AlreadyExistsException ex, HttpServletRequest request) {
        log.error("Already exists this resource: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new CustomErrorDetails<>(
                        ex.getMessage(),
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        null
                ));
    }

    @ExceptionHandler(StockLimitExceededException.class)
    public ResponseEntity<CustomErrorDetails<?>> limitExceededExceptionHandler(StockLimitExceededException ex, HttpServletRequest request) {
        log.error("Stock limit exceeded : ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new CustomErrorDetails<>(
                        ex.getMessage(),
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        null
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDetails<List<CustomFieldErrors>>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {

        log.error("Method argument not valid : ", ex);

        List<CustomFieldErrors> list = ex.getBindingResult().getFieldErrors().stream().map(err -> new CustomFieldErrors(err.getField(),err.getDefaultMessage())).toList();

        CustomErrorDetails<List<CustomFieldErrors>> customErrorDetails = new CustomErrorDetails<>(
                "Arguments invalid informations below.",
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                list

        );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(customErrorDetails);
    }

    public record CustomFieldErrors(String field,
                                    String message) {
    }


}
