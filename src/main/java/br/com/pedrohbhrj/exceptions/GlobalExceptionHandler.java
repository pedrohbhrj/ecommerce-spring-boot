package br.com.pedrohbhrj.exceptions;

import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
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
        log.error("Internal server error in route {}:{} ", request.getRequestURI(), ex.getMessage());
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

        log.warn("Resource not found , route {}: {}", request.getRequestURI(), ex.getMessage());

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
        log.warn("Already exists this resource route {}: {} ", request.getRequestURI(), ex.getMessage());
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
        log.warn("Stock limit exceeded route {}: {}", request.getRequestURI(), ex.getMessage());
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorDetails<?>> accessDeniedException(AccessDeniedException ex, HttpServletRequest request) {

        log.warn("Access denied route {}: {}", request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                .body(new CustomErrorDetails<>("Unathorized route.",
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.FORBIDDEN.value(),
                        null));
    }

    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<CustomErrorDetails<?>> illegalExceptionHandler(RuntimeException ex, HttpServletRequest request) {
        log.warn("Invalid state/argument on route {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new CustomErrorDetails<>(
                        ex.getMessage(),
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        null
                ));
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<CustomErrorDetails<?>> stripeExceptionHandler(StripeException ex, HttpServletRequest request) {

        log.warn("Stripe api error on route {}: {}", request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value())
                .body(new CustomErrorDetails<>("Unathorized route.",
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.BAD_GATEWAY.value(),
                        null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDetails<List<CustomFieldErrors>>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {

        log.warn("Method argument not valid route {}: {}", request.getRequestURI(), ex.getMessage());

        List<CustomFieldErrors> list = ex.getBindingResult().getFieldErrors().stream().map(err -> new CustomFieldErrors(err.getField(), err.getDefaultMessage())).toList();

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorDetails<?>> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("Malformed JSON payload on route {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new CustomErrorDetails<>(
                        "The body requisition is invalid or has malformed data.",
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        null
                ));
    }

    public record CustomFieldErrors(String field,
                                    String message) {
    }


}
