package edu.otaciotarelho.pointofsale.domain.external;

import edu.otaciotarelho.pointofsale.business.Type;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Promotion {

    private String id;
    private Type type;
    private Integer requiredQty;
    private BigDecimal price;
    private Integer free_qty;
    private BigDecimal amount;

}
