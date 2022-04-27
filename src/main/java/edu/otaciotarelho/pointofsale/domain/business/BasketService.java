package edu.otaciotarelho.pointofsale.domain.business;

import edu.otaciotarelho.pointofsale.domain.checkout.Basket;

import java.util.UUID;

public interface BasketService {

    Basket addItemToBasket(UUID basketId, String itemId, int amount);
    Basket removeItemFromBasket(UUID basketId, String itemId);
    Basket createBasket();
    void removeBasket(UUID id);
    Basket checkout(UUID id);

}
