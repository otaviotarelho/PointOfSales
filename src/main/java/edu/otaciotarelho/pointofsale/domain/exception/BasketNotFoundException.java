package edu.otaciotarelho.pointofsale.domain.exception;

public class BasketNotFoundException extends RuntimeException{

    public BasketNotFoundException() {
        super("Basket Not Available, Initiate new order");
    }
}
