package edu.otaciotarelho.pointofsale.api.handler;

import edu.otaciotarelho.pointofsale.domain.exception.BasketNotFoundException;
import edu.otaciotarelho.pointofsale.domain.exception.ProductNotFoundException;
import edu.otaciotarelho.pointofsale.domain.exception.StandardError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.servlet.function.ServerResponse.status;

@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(BasketNotFoundException.class)
    public ServerResponse notFoundBasket(BasketNotFoundException e, HttpServletRequest request){
        var err  = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(NOT_FOUND.value())
                .error("Basket Not Found")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return status(NOT_FOUND).body(err);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ServerResponse notFoundProduct(ProductNotFoundException e, HttpServletRequest request){
        var err  = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(NOT_FOUND.value())
                .error("Product Not Found")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return status(NOT_FOUND).body(err);
    }
}
