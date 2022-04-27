package edu.otaciotarelho.pointofsale.domain.business;

import edu.otaciotarelho.pointofsale.domain.checkout.Basket;
import edu.otaciotarelho.pointofsale.domain.checkout.dto.BasketDTO;

public interface CheckoutService {

    BasketDTO checkout(Basket basket);

}
