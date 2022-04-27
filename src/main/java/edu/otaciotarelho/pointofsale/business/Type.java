package edu.otaciotarelho.pointofsale.business;

import edu.otaciotarelho.pointofsale.domain.checkout.dto.ItemDTO;
import edu.otaciotarelho.pointofsale.domain.external.Promotion;

import java.math.BigDecimal;
import java.util.function.BiFunction;

import static java.math.RoundingMode.CEILING;

public enum Type  implements BiFunction<ItemDTO, Promotion, BigDecimal> {
    FLAT_PERCENT ("FLAT_PERCENT") {
        @Override
        public BigDecimal apply(ItemDTO item, Promotion promotion){
            return item.getPrice().multiply(promotion.getAmount().divide(BigDecimal.valueOf(100), CEILING));
        }
    },
    QTY_BASED_PRICE_OVERRIDE ("QTY_BASED_PRICE_OVERRIDE") {
        @Override
        public BigDecimal apply (ItemDTO item, Promotion promotion){
            if(item.getQuantity() < promotion.getRequiredQty()){
                return item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            }

            return promotion.getPrice().multiply(
                    BigDecimal.valueOf(item.getQuantity()/ promotion.getRequiredQty())
            );
        }
    },
    BUY_X_GET_Y_FREE ("BUY_X_GET_Y_FREE"){
        @Override
        public BigDecimal apply (ItemDTO item, Promotion promotion){
            if(item.getQuantity() < promotion.getRequiredQty()){
                return item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            }

            return item.getPrice().multiply(
                    BigDecimal.valueOf((long) (item.getQuantity() / promotion.getRequiredQty())
                            * promotion.getFree_qty())
            );
        }

    };

    private final String typePromo;

    private Type(final String typePromo) {
        this.typePromo = typePromo;
    }

    public String getTypePromo() {
        return typePromo;
    }

}
