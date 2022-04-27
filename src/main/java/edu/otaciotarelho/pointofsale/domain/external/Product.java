package edu.otaciotarelho.pointofsale.domain.external;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class Product {
    private String id;
    private String name;
    private BigDecimal price;
}
