package edu.otaciotarelho.pointofsale.domain.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.otaciotarelho.pointofsale.business.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    private String id;

    private Type type;

    @JsonProperty(value = "required_qty")
    private Integer requiredQty;

    private BigDecimal price;

    @JsonProperty(value = "free_qty")
    private Integer freeQty;

    private BigDecimal amount;

}
