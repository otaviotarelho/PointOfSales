package edu.otaciotarelho.pointofsale.business;

import edu.otaciotarelho.pointofsale.business.repository.BasketRepository;
import edu.otaciotarelho.pointofsale.domain.business.BasketService;
import edu.otaciotarelho.pointofsale.domain.checkout.Basket;
import edu.otaciotarelho.pointofsale.domain.checkout.Item;
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

    private final BasketRepository repository;

    @Autowired
    public BasketServiceImpl(ProductService productService,
                             BasketRepository repository) {
        this.productService = productService;
        this.repository = repository;
    }

    @Override
    public Basket addItemToBasket(UUID basketId, String productId, int amount) {
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

        found.increaseQuantity();

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
    public Basket checkout(UUID id) {
        return repository.findById(id)
                .orElseThrow(BasketNotFoundException::new);

        //get all products
        //calculate promotions
        //return to user
    }
}
