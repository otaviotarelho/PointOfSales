package edu.otaciotarelho.pointofsale.business.service;

import edu.otaciotarelho.pointofsale.domain.business.CheckoutService;
import edu.otaciotarelho.pointofsale.domain.checkout.Basket;
import edu.otaciotarelho.pointofsale.domain.checkout.Item;
import edu.otaciotarelho.pointofsale.domain.checkout.dto.BasketDTO;
import edu.otaciotarelho.pointofsale.domain.checkout.dto.ItemDTO;
import edu.otaciotarelho.pointofsale.domain.exception.EmptyBasketException;
import edu.otaciotarelho.pointofsale.domain.external.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service("checkout/v1")
public class CheckoutServiceImpl implements CheckoutService {

    @Qualifier("productIntegration/v1")
    private final ProductService productService;


    @Autowired
    public CheckoutServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    public BasketDTO checkout(Basket basket){

        if(basket.getItems() == null || basket.getItems().isEmpty()){
            throw new EmptyBasketException();
        }

        var item = basket.getItems().stream()
                .map(this::getItem)
                .collect(Collectors.toList());


        var checkoutBasket = BasketDTO.builder()
                .basketId(basket.getId())
                .items(item)
                .build();

        return calculateBasket(checkoutBasket);
    }

    private BasketDTO calculateBasket(BasketDTO checkoutBasket) {

        var amount = checkoutBasket
                .getItems()
                .stream()
                .mapToDouble(
                        item -> item.getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                                .doubleValue())
                .sum();

        var amountPromo = calculatePromos(checkoutBasket.getItems());

        var amountPayable = BigDecimal.valueOf(amount)
                .subtract(amountPromo);

        checkoutBasket.setAmount(BigDecimal.valueOf(amount));
        checkoutBasket.setPromosAmount(amountPromo);
        checkoutBasket.setPayableAmount(amountPayable);

        return checkoutBasket;
    }

    private BigDecimal calculatePromos(List<ItemDTO> item) {
        var total = item.stream().mapToDouble(
                i-> {
                        if(i.getPromotions() == null) {
                            return 0;
                        }

                        return i.getPromotions()
                            .stream()
                            .mapToDouble(promo -> promo.getType()
                                    .apply(i, promo)
                                    .doubleValue()
                            )
                            .sum();
                }
        ).sum();

        return BigDecimal.valueOf(total);
    }

    private ItemDTO getItem(Item item) {
        var product = productService.getProduct(item.getProductId());

        return ItemDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .promotions(product.getPromotions())
                .price(product.getPrice())
                .quantity(item.getQuantity())
                .build();
    }


}
