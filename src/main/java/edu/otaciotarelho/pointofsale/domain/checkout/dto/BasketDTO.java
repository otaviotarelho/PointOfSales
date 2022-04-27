package edu.otaciotarelho.pointofsale.domain.checkout.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BasketDTO {

    private UUID basketId;

    private List<ItemDTO> items;

    private BigDecimal amount;

    private BigDecimal promosAmount;

    private BigDecimal payableAmount;

}
