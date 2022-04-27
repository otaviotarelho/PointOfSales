package edu.otaciotarelho.pointofsale.domain.exception;

public class EmptyBasketException extends RuntimeException{

    public EmptyBasketException() {
        super("No item added to the basket.");
    }
}
