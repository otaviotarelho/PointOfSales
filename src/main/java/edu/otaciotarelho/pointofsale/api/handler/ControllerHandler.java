package edu.otaciotarelho.pointofsale.api.handler;

import edu.otaciotarelho.pointofsale.domain.exception.BasketNotFoundException;
import edu.otaciotarelho.pointofsale.domain.exception.EmptyBasketException;
import edu.otaciotarelho.pointofsale.domain.exception.ProductNotFoundException;
import edu.otaciotarelho.pointofsale.domain.exception.StandardError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(BasketNotFoundException.class)
    public ResponseEntity<StandardError> notFoundBasket(BasketNotFoundException e, HttpServletRequest request){
        var err  = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(NOT_FOUND.value())
                .error("Basket Not Found")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(err);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<StandardError> notFoundProduct(ProductNotFoundException e, HttpServletRequest request){
        var err  = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(NOT_FOUND.value())
                .error("Product Not Found")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(err);
    }

    @ExceptionHandler(EmptyBasketException.class)
    public ResponseEntity<StandardError> notFoundProduct(EmptyBasketException e, HttpServletRequest request){
        var err  = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(NOT_FOUND.value())
                .error("Empty Basket")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(err);
    }
}
