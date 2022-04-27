package edu.otaciotarelho.pointofsale.domain.business;

import edu.otaciotarelho.pointofsale.domain.checkout.Basket;
import edu.otaciotarelho.pointofsale.domain.checkout.dto.BasketDTO;

import java.util.UUID;

public interface BasketService {

    Basket addItemToBasket(UUID basketId, String itemId, int amount);
    Basket removeItemFromBasket(UUID basketId, String itemId);
    Basket createBasket();
    void removeBasket(UUID id);
    BasketDTO checkout(UUID id);

}
