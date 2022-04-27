package edu.otaciotarelho.pointofsale.domain.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException() {
        super("Product not found in wiredmock");
    }
}
