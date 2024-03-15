package org.openschool.consumer_service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@ToString
public class Product {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Category category;
}
