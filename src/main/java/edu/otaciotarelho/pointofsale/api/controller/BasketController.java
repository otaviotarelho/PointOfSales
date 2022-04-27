package edu.otaciotarelho.pointofsale.api.controller;

import edu.otaciotarelho.pointofsale.domain.business.BasketService;
import edu.otaciotarelho.pointofsale.domain.checkout.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@RestController
@RequestMapping(value="/basket")
public class BasketController {

    private final BasketService basketService;

    @Autowired
    BasketController(@Qualifier("basketService/v1") final BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping(value="/{basket_id}/product/{product_id}")
    ServerResponse addItem(
            @PathVariable("basket_id") final UUID id,
            @PathVariable("product_id")   final String productId,
            @RequestParam("qty") final Integer quantity
    ) {
        basketService.addItemToBasket(id, productId, quantity);

        return ok().build();
    }

    @DeleteMapping(value = "/{basket_id}/product/{product_id}")
    ServerResponse deleteItem(@PathVariable("basket_id") final UUID id,
                    @PathVariable("product_id") final String productId) {
        basketService.removeItemFromBasket(id, productId);
        return ok().build();
    }

    @PostMapping
    ResponseEntity<Basket> createBasket(){
        final var basket = basketService.createBasket();

        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest().
                path("/{id}").
                buildAndExpand(basket.getId())
                .toUri();

        return created(uri).body(basket);
    }

    @DeleteMapping(value = "/{basket_id}")
    ServerResponse removeBasket(@PathVariable("basket_id") final UUID id){
        basketService.removeBasket(id);
        return ok().build();
    }

    @PostMapping(value = "/checkout/{basket_id}")
    ServerResponse finishOrder(@PathVariable("basket_id") final UUID id){
        return ok().body(basketService.checkout(id));
    }

}
