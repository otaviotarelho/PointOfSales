package edu.otaciotarelho.pointofsale.business.service;

import edu.otaciotarelho.pointofsale.business.repository.BasketRepository;
import edu.otaciotarelho.pointofsale.domain.business.BasketService;
import edu.otaciotarelho.pointofsale.domain.business.CheckoutService;
import edu.otaciotarelho.pointofsale.domain.checkout.Basket;
import edu.otaciotarelho.pointofsale.domain.checkout.Item;
import edu.otaciotarelho.pointofsale.domain.checkout.dto.BasketDTO;
import edu.otaciotarelho.pointofsale.domain.exception.BasketNotFoundException;
import edu.otaciotarelho.pointofsale.domain.external.Product;
import edu.otaciotarelho.pointofsale.domain.external.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service("basketService/v1")
public class BasketServiceImpl implements BasketService {

    @Qualifier("productIntegration/v1")
    private final ProductService productService;

    @Qualifier("checkout/v1")
    private final CheckoutService checkoutService;

    private final BasketRepository repository;

    @Autowired
    public BasketServiceImpl(ProductService productService,
                             BasketRepository repository,
                             CheckoutService checkoutService) {
        this.productService = productService;
        this.repository = repository;
        this.checkoutService = checkoutService;
    }

    @Override
    public Basket addItemToBasket(UUID basketId, String productId, int quantity) {
        var basket = repository.findById(basketId)
                .orElseThrow(BasketNotFoundException::new);

        if(basket.getItems().isEmpty()){
            basket.setItems(new ArrayList<>());
        }

        var found = basket.getItems()
                .stream()
                .filter( item -> item.getProductId().equals(productId))
                .findAny()
                .orElse(new Item());

        found.increaseQuantity(quantity);

        if(found.getId() == null){
            Product product = productService.getProduct(productId);
            found.setProductId(product.getId());
        }

        basket.addItem(found);

        return repository.save(basket);
    }


    @Override
    public Basket removeItemFromBasket(UUID basketId, String itemId) {
        var basket = repository.findById(basketId)
                .orElseThrow(BasketNotFoundException::new);

        basket.getItems().removeIf( item -> item.getProductId().equals(itemId));

        repository.save(basket);

        return basket;
    }

    @Override
    public Basket createBasket() {
        return repository.save(new Basket());
    }

    @Override
    public void removeBasket(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public BasketDTO checkout(UUID id) {
        var basket =  repository.findById(id)
                .orElseThrow(BasketNotFoundException::new);
        return checkoutService.checkout(basket);
    }

}
