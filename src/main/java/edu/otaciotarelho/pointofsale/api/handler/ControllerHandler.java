package edu.otaciotarelho.pointofsale.api.handler;

import edu.otaciotarelho.pointofsale.domain.exception.BasketNotFoundException;
import edu.otaciotarelho.pointofsale.domain.exception.EmptyBasketException;
import edu.otaciotarelho.pointofsale.domain.exception.ProductNotFoundException;
import edu.otaciotarelho.pointofsale.domain.exception.StandardError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            BasketNotFoundException.class,
            ProductNotFoundException.class,
            EmptyBasketException.class
    })
    public ResponseEntity<StandardError> handleException(BasketNotFoundException e, WebRequest request){
        var err  = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(NOT_FOUND.value())
                .error("Error occurred processing request")
                .message(e.getMessage())
                .path(request.getContextPath())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(err);
    }
}
