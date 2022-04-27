package edu.otaciotarelho.pointofsale.business;

import edu.otaciotarelho.pointofsale.business.repository.BasketRepository;
import edu.otaciotarelho.pointofsale.business.service.BasketServiceImpl;
import edu.otaciotarelho.pointofsale.business.service.CheckoutServiceImpl;
import edu.otaciotarelho.pointofsale.domain.checkout.Basket;
import edu.otaciotarelho.pointofsale.domain.exception.BasketNotFoundException;
import edu.otaciotarelho.pointofsale.domain.external.Product;
import edu.otaciotarelho.pointofsale.domain.external.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class BasketServiceImplTest {

    private final ProductService productService = Mockito.mock(ProductService.class);

    private final BasketRepository repository;

    private final BasketServiceImpl basketService;

    private final CheckoutServiceImpl checkoutService;

    private Basket basket;

    private Product product;

    @Autowired
    public BasketServiceImplTest(BasketRepository repository) {
        this.repository = repository;
        this.checkoutService = new CheckoutServiceImpl(productService);
        basketService = new BasketServiceImpl(productService, repository, checkoutService);
    }

    @BeforeEach
    void init(){
        product = mockedProduct();
        basket = basketService.createBasket();
    }

    @AfterEach
    void finish(){
        basketService.removeBasket(basket.getId());
    }

    @Test
    void should_insert_item_in_existing_basket_successfully(){

       when(productService.getProduct(anyString())).thenReturn(product);

       basket = basketService.addItemToBasket(basket.getId(), product.getId(), 1);

        assertAll(
                () -> assertNotNull(basket),
                () -> assertNotNull(basket.getItems()),
                () -> assertEquals(1, basket.getItems().size())
        );
    }

    @Test
    void should_remove_item_in_existing_basket(){
        when(productService.getProduct(anyString())).thenReturn(product);

        basket = basketService.addItemToBasket(basket.getId(), product.getId(), 1);

        Basket result = basketService.removeItemFromBasket(basket.getId(), product.getId());

        assertAll(
                () -> assertNotNull(result),
                () -> assertNotNull(result.getItems()),
                () -> assertEquals(0, result.getItems().size())
        );
    }

    @Test
    void should_show_basket_content(){
        when(productService.getProduct(anyString())).thenReturn(product);

        basket = basketService.addItemToBasket(basket.getId(), product.getId(), 1);

        var basketDTO = basketService.checkout(basket.getId());

        assertAll(
                () -> assertNotNull(basketDTO),
                () -> assertNotNull(basketDTO.getItems()),
                () -> assertEquals(1, basketDTO.getItems().size())
        );
    }

    @Test
    void should_remove_basket(){
        Basket newBasket = basketService.createBasket();

        basketService.removeBasket(newBasket.getId());

        assertThrows(BasketNotFoundException.class, () -> basketService.checkout(newBasket.getId()));
    }


    private Product mockedProduct() {
        return Product.builder()
                .id("Dwt5F7KAhi")
                .name("Amazing Pizza!")
                .price(BigDecimal.valueOf(1099))
                .build();
    }

}