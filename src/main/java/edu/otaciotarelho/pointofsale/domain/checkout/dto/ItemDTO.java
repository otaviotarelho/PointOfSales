package edu.otaciotarelho.pointofsale.domain.checkout.dto;

import edu.otaciotarelho.pointofsale.domain.external.Promotion;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ItemDTO {

    private String productId;

    private String name;

    private int quantity;

    private BigDecimal price;

    @Transient
    public List<Promotion> promotions;
}
