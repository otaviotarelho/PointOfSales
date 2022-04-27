package edu.otaciotarelho.pointofsale.domain.external;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class Product {
    private String id;
    private String name;
    private BigDecimal price;
    private List<Promotion> promotions;
}
